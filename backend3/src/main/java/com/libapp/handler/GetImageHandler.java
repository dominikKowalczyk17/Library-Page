package com.libapp.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.libapp.repository.BookRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetImageHandler implements HttpHandler {
    private final BookRepository repository = new BookRepository();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Set CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            // Handle preflight requests for CORS
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("GET".equals(exchange.getRequestMethod())) {
            String query = exchange.getRequestURI().getQuery();
            String[] queryParams = query.split("=");

            if (queryParams.length == 2 && "isbn".equals(queryParams[0])) {
                try {
                    int isbn = Integer.parseInt(queryParams[1]);
                    String outputPath = "temp_image.jpg"; // Temporary path to store the image

                    // Fetch the image from the database
                    repository.getBookImage(isbn, outputPath);

                    // Set response headers and content type
                    exchange.getResponseHeaders().set("Content-Type", "image/jpeg");
                    byte[] imageBytes = Files.readAllBytes(Paths.get(outputPath));

                    exchange.sendResponseHeaders(200, imageBytes.length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(imageBytes);
                    }

                    // Clean up temporary image file
                    Files.deleteIfExists(Paths.get(outputPath));
                } catch (Exception e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, -1);
                }
            } else {
                exchange.sendResponseHeaders(400, -1); // Bad request if ISBN is missing or incorrect
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }
}
