package main.java.com.libapp.server;

import com.sun.net.httpserver.HttpServer;
import main.java.com.libapp.handler.AddBookHandler;
import main.java.com.libapp.handler.GetBookHandler;
import main.java.com.libapp.handler.PopularBooksHandler;
import main.java.com.libapp.handler.SearchBooksHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/api/books/add", new AddBookHandler());
        server.createContext("/api/books/search", new SearchBooksHandler());
        server.createContext("/api/books", new GetBookHandler());
        server.createContext("/api/books/popular", new PopularBooksHandler()); // New endpoint for popular books

        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port 8081");
    }
}
