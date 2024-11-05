package org.example.services;

import org.example.models.Book;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleFunction;

public class BookService {

    private final List<Book> books;

    public BookService(List<Book> books) {
        this.books = books;
    }

    public Optional<Book> getMaxQuantityBook() {
       return books.stream().max(Comparator.comparing(Book::getQuantity));
    }

    public Optional<Book> getMinQuantityBook() {
        return books.stream().min(Comparator.comparing(Book::getQuantity));
    }

    public Optional<Book> getBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title)) // case insensitive
                .findFirst();
    }

    public int sumBooksByQuantity() {
        return books.stream().mapToInt(Book::getQuantity).sum();
    }

    public double sumBooksByPrice() {
        return books.stream().mapToDouble(Book::getPrice).sum();
    }

    public double sumBooksByTotalPrice() {
        return books.stream().mapToDouble(book -> book.getPrice() * book.getQuantity()).sum();
    }

    public double sumBooksBy(List<Book> books, ToDoubleFunction<? super Book> func) {
        return books.stream().mapToDouble(func).sum();
    }




}
