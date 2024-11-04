package com.libapp.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CoverImagePopulator {
    private String imagesPath;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public CoverImagePopulator(String imagesPath, String dbUrl, String dbUser, String dbPassword) {
        this.imagesPath = imagesPath;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    // Method to establish a database connection
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    // Method to update cover image URLs for each book
    public void populateCoverImages() {
        File folder = new File(imagesPath);
        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("No files found in the directory.");
            return;
        }

        try (Connection conn = connect()) {
            for (File file : files) {
                if (!file.isFile()) continue; // Skip directories or non-files

                String filename = file.getName();
                String isbn = extractIsbn(filename);
                String url = generateCoverImageUrl(filename);

                updateCoverImageUrl(conn, isbn, url);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to extract ISBN from filename
    private String extractIsbn(String filename) {
        return filename.substring(0, filename.lastIndexOf('.'));
    }

    // Helper method to generate the image URL
    private String generateCoverImageUrl(String filename) {
        return "http://localhost:8081/api/books/image/" + filename;
    }

    // Method to update the database record for a given ISBN
    private void updateCoverImageUrl(Connection conn, String isbn, String url) {
        String query = "UPDATE books SET coverImageUrl = ? WHERE isbn = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, url);
            stmt.setString(2, isbn);
            stmt.executeUpdate();
            System.out.println("Updated cover image URL for ISBN: " + isbn);
        } catch (SQLException e) {
            System.err.println("Error updating cover image URL for ISBN: " + isbn);
            e.printStackTrace();
        }
    }
}
