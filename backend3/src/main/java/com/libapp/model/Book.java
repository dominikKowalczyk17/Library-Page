package com.libapp.model;

import com.google.gson.Gson;

public class Book {
    private String isbn; // Consider keeping ISBN as a String
    private String title;
    private String genre;
    private String description;
    private String author;
    private int popularityScore;
    private String imagePath;

    // Constructor
    public Book(String isbn, String title, String genre, String description, String author, int popularityScore, String imagePath) {
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.author = author;
        this.popularityScore = popularityScore;
        this.imagePath = imagePath;
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public int getPopularityScore() {
        return popularityScore;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getImagePath() {
        return imagePath;
    }
}
