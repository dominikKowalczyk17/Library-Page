package com.libapp.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.libapp.model.Book;
import com.libapp.repository.BookRepository;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class AddBookHandler implements HttpHandler {
    private final BookRepository repository = new BookRepository();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Set CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            // Handle preflight requests
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(body);

            // Use random number as ISBN (from 1 to 100)
            int isbn = new Random().nextInt(100) + 1; // Random ISBN between 1 and 100
            Book book = new Book(
                    isbn, // Use generated ISBN
                    json.getString("name"),
                    json.getString("genre"),
                    json.getString("description"),
                    json.getString("author"),
                    json.optInt("popularity_score", 0) // Default popularity score to 0 if not provided
            );

            repository.addBook(book);

            String response = "Book added successfully with ISBN: " + isbn;
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream output = exchange.getResponseBody()) {
                output.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
        }
    }
}

