package com.libapp.repository;

import com.libapp.model.Book;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
                        rs.getInt("popularity_score")
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
                        rs.getInt("popularity_score")
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
                popularBooks.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("description"),
                        rs.getString("author"),
                        rs.getInt("popularity_score")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return popularBooks;
    }

    public void getBookImage(int isbn, String outputPath) throws Exception {
        String sql = "SELECT image_data FROM public.books WHERE isbn = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                byte[] imageData = rs.getBytes("image_data");

                if (imageData != null) {
                    try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                        fos.write(imageData);
                        System.out.println("Image saved to " + outputPath);
                    }
                } else {
                    System.out.println("No image found for ISBN: " + isbn);
                }
            } else {
                System.out.println("No book found with ISBN: " + isbn);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching image data: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }
}
