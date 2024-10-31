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
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(body);

            // Generate a random ISBN as a String (if you keep it as String)
            String isbn = String.valueOf(new Random().nextInt(100) + 1); // Random ISBN as String
            Book book = new Book(
                    isbn,
                    json.getString("title"),
                    json.getString("genre"),
                    json.getString("description"),
                    json.getString("author"),
                    json.optInt("popularity_score", 0)
            );

            repository.addBook(book);

            String response = "Book added successfully with ISBN: " + isbn;
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream output = exchange.getResponseBody()) {
                output.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }
}
