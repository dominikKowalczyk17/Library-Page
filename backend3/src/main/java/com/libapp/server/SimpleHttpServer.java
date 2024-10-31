package com.libapp.server;

import com.libapp.handler.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/api/books/add", new AddBookHandler());
        server.createContext("/api/books/search", new SearchBooksHandler());
        server.createContext("/api/books", new GetBookHandler());
        server.createContext("/api/books/popular", new PopularBooksHandler());
        server.createContext("/api/books/image", new GetImageHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port 8081");
    }
}

