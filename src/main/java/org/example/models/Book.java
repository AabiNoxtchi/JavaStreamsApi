package org.example.models;

import org.example.Util;

public class Book {

    private final double price;
    private final String title;
    private final String author;
    private final String category;
    private final int quantity;

    public Book(String title, String category, String author, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = (double) Math.round(price * 10) / 10;
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


    @Override
    public String toString() {
        return String.format("title = %-15s, author = %-15s, category = %-15s, price = %6s\t, quantity = %-15s",
                title, author, category, Util.format(price), quantity);
    }

    public String toQuantityString() {
        return String.format("title = %-15s, quantity = %-15s",
                title, quantity);
    }

    public String toPriceString() {
        return String.format("title = %-15s, price = %6s",
                title, Util.format(price));
    }
}
