package com.libapp.repository;

import com.libapp.model.Book;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BookRepository {
    private String URL;
    private String USER;
    private String PASSWORD;

    private void loadDBConfig() {
        Properties properties = new Properties();
        try(FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            this.URL = properties.getProperty("db.url");
            this.USER = properties.getProperty("db.username");
            this.PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            System.out.println("Error loading database config: " + e.getMessage());
        }
    }

    private Connection connect() throws SQLException {
        loadDBConfig();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }

        // Establish connection and print a message
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connected to the database.");
        return conn;
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO books (isbn, title, genre, description, author, popularity_score) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (isbn) DO NOTHING";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getGenre());
            pstmt.setString(4, book.getDescription());
            pstmt.setString(5, book.getAuthor());
            pstmt.setInt(6, book.getPopularityScore());
            pstmt.setString(7, book.getImagePath());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Book findByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("description"),
                        rs.getString("author"),
                        rs.getInt("popularity_score"),
                        rs.getString("image_path")
                );
            } else {
                System.out.println("No book found with ISBN: " + isbn);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public List<Book> findByKeyword(String keyword) {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE LOWER(?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookList.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("description"),
                        rs.getString("author"),
                        rs.getInt("popularity_score"),
                        rs.getString("image_path")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookList;
    }

    public List<Book> findPopularBooks() {
        String sql = "SELECT * FROM public.books WHERE popularity_score > 9 ORDER BY popularity_score DESC LIMIT 10";
        List<Book> popularBooks = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String rawImagePath = rs.getString("image_path");
                String imagePath = rawImagePath != null ? rawImagePath.replace("\\", "/") : null;
                popularBooks.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("description"),
                        rs.getString("author"),
                        rs.getInt("popularity_score"),
                        imagePath
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return popularBooks;
    }

//    public void updateImagePaths() {
//        String selectSQL = "SELECT isbn FROM books";
//        String updateSQL = "UPDATE books SET image_path = ? WHERE isbn = ?";
//        String IMAGE_DIRECTORY = "images/book_covers/";
//
//        String[] extensions = {".jpg", ".jpeg", ".jfif"};
//
//        try (Connection conn = connect();
//             PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
//             PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
//
//            ResultSet rs = selectStmt.executeQuery();
//
//            while (rs.next()) {
//                String isbn = rs.getString("isbn");
//                String relativePath = findRelativeImagePath(isbn, IMAGE_DIRECTORY, extensions);
//
//                if (relativePath != null) {
//                    updateStmt.setString(1, relativePath);
//                    updateStmt.setString(2, isbn);
//                    updateStmt.executeUpdate();
//                    System.out.println("Updated image path for ISBN: " + isbn);
//                } else {
//                    System.out.println("Image not found for ISBN: " + isbn);
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error updating image paths: " + e.getMessage());
//        }
//    }
//
//    private String findRelativeImagePath(String isbn, String imageDirectory, String[] extensions) {
//        for (String ext : extensions) {
//            Path imagePath = Paths.get(imageDirectory, isbn + ext);
//            if (imagePath.toFile().exists()) {
//                return imagePath.toString();
//            }
//        }
//        return null;
//    }

}
