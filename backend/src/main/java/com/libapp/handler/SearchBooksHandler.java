package main.java.com.libapp.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import main.java.com.libapp.model.Book;
import main.java.com.libapp.repository.BookRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SearchBooksHandler implements HttpHandler {
    private final BookRepository repository = new BookRepository();
    private final Gson gson = new Gson(); // Initialize Gson for JSON conversion

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Set CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            // Handle preflight requests
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("GET".equals(exchange.getRequestMethod())) {
            URI requestURI = exchange.getRequestURI();
            String query = requestURI.getQuery();

            String keyword = "";
            if (query != null && query.contains("keyword=")) {
                String[] params = query.split("&");
                for (String param : params) {
                    if (param.startsWith("keyword=")) {
                        // Decode the keyword
                        keyword = URLDecoder.decode(param.split("=")[1], StandardCharsets.UTF_8);
                        break;
                    }
                }
            }

            ArrayList<Book> books = repository.findByKeyword(keyword);

            // Convert the list of books to JSON format
            String jsonResponse = gson.toJson(books);

            // Set response headers and status
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, jsonResponse.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(jsonResponse.getBytes());
            }
        } else {
            // Handle unsupported methods
            exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
        }
    }
}
