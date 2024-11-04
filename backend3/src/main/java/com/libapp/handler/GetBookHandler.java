package com.libapp.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.libapp.model.Book;
import com.libapp.repository.BookRepository;

import java.io.IOException;
import java.io.OutputStream;

public class GetBookHandler implements HttpHandler {
    private final BookRepository repository = new BookRepository();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Set CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("GET".equals(exchange.getRequestMethod())) {
            String path = exchange.getRequestURI().getPath();
            String isbnStr = path.substring(path.lastIndexOf("/") + 1);

            // Use the ISBN as a String directly
            Book book = repository.findByIsbn(isbnStr); // No conversion to int

            String response;
            if (book != null) {
                response = book.toJson();
                exchange.sendResponseHeaders(200, response.getBytes().length);
            } else {
                response = "{ \"message\": \"Book not found\" }";
                exchange.sendResponseHeaders(404, response.getBytes().length);
            }
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            System.out.println("Method not allowed.");
        }
    }
}
