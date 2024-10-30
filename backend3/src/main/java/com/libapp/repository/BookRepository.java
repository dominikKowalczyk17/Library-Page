package com.libapp.repository;

import com.libapp.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/LibApp";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private Connection connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public void addBook(Book book) {
        String sql = "INSERT INTO library.books (isbn, name, genre, description, author, popularity_score) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, book.getIsbn());
            pstmt.setString(2, book.getName());
            pstmt.setString(3, book.getGenre());
            pstmt.setString(4, book.getDescription());
            pstmt.setString(5, book.getAuthor());
            pstmt.setInt(6, book.getPopularityScore());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Book findByIsbn(int isbn) {
        String sql = "SELECT * FROM library.books WHERE isbn = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getInt("isbn"),
                        rs.getString("name"),
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

    public ArrayList<Book> findByKeyword(String keyword) {
        ArrayList<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM library.books WHERE LOWER(name) LIKE LOWER(?)";
        System.out.println("Searching for keyword: " + keyword); // Debugging output
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookList.add(new Book(
                        rs.getInt("isbn"), // Updated to getInt
                        rs.getString("name"),
                        rs.getString("genre"),
                        rs.getString("description"),
                        rs.getString("author"),
                        rs.getInt("popularity_score")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Books found: " + bookList.size()); // Debugging output
        return bookList;
    }

    public List<Book> findPopularBooks() {
        String sql = "SELECT * FROM library.books WHERE popularity_score > 50 ORDER BY popularity_score DESC LIMIT 10";
        List<Book> popularBooks = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                popularBooks.add(new Book(
                        rs.getInt("isbn"), // Updated to getInt
                        rs.getString("name"),
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
}

