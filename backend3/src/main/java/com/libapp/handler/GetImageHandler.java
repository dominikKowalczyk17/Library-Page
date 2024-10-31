package com.libapp.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GetImageHandler implements HttpHandler {
    private static final String IMAGE_DIRECTORY = "C:\\Users\\Dominik\\Desktop\\Projects\\Library-Page\\backend3\\images\\book_covers";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extract the image filename from the request URI
        String path = exchange.getRequestURI().getPath();
        String filename = path.substring(path.lastIndexOf("/") + 1); // Get the file name from the path

        // Create the file object
        File file = new File(IMAGE_DIRECTORY, filename);

        if (file.exists() && !file.isDirectory()) {
            // Set the response headers
            exchange.getResponseHeaders().add("Content-Type", "image/jpeg");
            exchange.sendResponseHeaders(200, file.length());

            // Read the file and write it to the output stream
            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream os = exchange.getResponseBody()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } else {
            // If the file does not exist, send a 404 response
            String response = "Image not found";
            exchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
