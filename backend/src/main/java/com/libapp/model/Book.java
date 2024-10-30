package main.java.com.libapp.model;

import com.google.gson.Gson;

public class Book {
    private int isbn;
    private String name;
    private String genre;
    private String description;
    private String author;
    private int popularityScore;

    // Constructor
    public Book(int isbn, String name, String genre, String description, String author, int popularityScore) {
        this.isbn = isbn;
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.author = author;
        this.popularityScore = popularityScore;
    }

    public int getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
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
}
