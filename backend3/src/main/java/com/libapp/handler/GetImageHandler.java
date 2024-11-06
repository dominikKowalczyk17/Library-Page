package com.libapp.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetImageHandler implements HttpHandler {
    private static final String IMAGE_DIRECTORY = "images/book_covers/";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String filename = path.substring("/api/books/image/".length());

        // Find the image using the relative path
        File file = findImageFile(filename);

        if (file != null && file.exists()) {
            String contentType = determineContentType(file.getName());
            exchange.getResponseHeaders().add("Content-Type", contentType);
            exchange.sendResponseHeaders(200, file.length());

            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream os = exchange.getResponseBody()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } else {
            String response = "Image not found";
            exchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private File findImageFile(String filename) {
        String[] extensions = {".jpg", ".jpeg", ".jfif"};
        for (String ext : extensions) {
            Path imagePath = Paths.get(System.getProperty("user.dir"), IMAGE_DIRECTORY, filename + ext);
            System.out.println(imagePath);
            File file = imagePath.toFile();
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    private String determineContentType(String filename) {
        if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg") || filename.toLowerCase().endsWith(".jfif")) {
            return "image/jpeg";
        }
        return "application/octet-stream";
    }
}
