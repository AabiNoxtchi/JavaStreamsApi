package org.example.models;

public class Book {

    private final double price;
    private final String title;
    private final String author;
    private final String category;
    private int quantity;

    public Book(String title, String category, String author, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
