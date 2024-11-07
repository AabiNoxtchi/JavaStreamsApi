package org.example.services;

import org.example.Util;
import org.example.models.Book;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookService {

    private final List<Book> books;

    public BookService() {
        this.books = initBooks();
    }

    public Optional<Book> getMaxQuantityBook() {
       return books.stream().max(Comparator.comparing(Book::getQuantity));
    }

    public Optional<Book> getMaxPriceBook() {
        return books.stream().max(Comparator.comparing(Book::getPrice));
    }

    public Optional<Book> getMinQuantityBook() {
        return books.stream().min(Comparator.comparing(Book::getQuantity));
    }

    public Optional<Book> getMinPriceBook() {
        return books.stream().min(Comparator.comparing(Book::getPrice));
    }

    public <U extends Comparable<? super U>> Optional<Book> getMaxBy(Function<Book,? extends U> func) {
        return books.stream().max(Comparator.comparing(func));
    }

    public <U extends Comparable<? super U>> Optional<Book> getMinBy(Function<Book,? extends U> func) {
        return books.stream().min(Comparator.comparing(func));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public List<Book> getBooksBy(List<String> bookTitles) {
        return bookTitles.stream()
                .map(title -> books.stream().filter(book -> book.getTitle().equals(title)).findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public List<Book> getBooksBy(String search) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                                book.getAuthor().toLowerCase().contains(search.toLowerCase()) ||
                                book.getCategory().toLowerCase().contains(search.toLowerCase()))
                .toList();
    }

    public Optional<Book> getBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public int sumBooksByQuantity() {
        return books.stream().mapToInt(Book::getQuantity).sum();
    }

    public double getAveragePrice() {
        return (books.stream().map(Book::getPrice).reduce(0d, Double::sum)) / books.size();
    }

    public double sumBooksByTotalValue() {
        return books.stream().mapToDouble(book -> book.getPrice() * book.getQuantity()).sum();
    }

    public Map<Boolean, List<Book>> partitionBooksBy(Predicate<? super Book> predicate) {
        return books.stream()
                .collect(Collectors.partitioningBy(predicate));
    }

    public Map<Boolean, List<String>> partitionBookTitlesBy(Predicate<? super Book> predicate) {
        return books.stream()
                .collect(Collectors.partitioningBy(predicate,
                        Collectors.mapping(Book::getTitle, Collectors.toList())));
    }

    public Map<Boolean, Integer> partitionBookCountsBy(Predicate<? super Book> predicate) {
        return books.stream()
                .collect(Collectors.partitioningBy(predicate,
                        Collectors.summingInt(Book::getQuantity)));
    }

    public Map<String, String> getBooksStats() {

        Map<String, String> stats = new LinkedHashMap<>();

        getMaxBy(Book::getQuantity).ifPresent(book -> stats.put("getMaxBy(quantity)", book.toQuantityString()));
        getMaxQuantityBook().ifPresent(book -> stats.put("getMaxQuantityBook()", book.toQuantityString()));

        getMinBy(Book::getQuantity).ifPresent(book -> stats.put("getMinBy(quantity)", book.toQuantityString()));
        getMinQuantityBook().ifPresent(book -> stats.put("getMinQuantityBook()", book.toQuantityString()));

        getMaxBy(Book::getPrice).ifPresent(book -> stats.put("getMaxBy(price)", book.toPriceString()));
        getMaxPriceBook().ifPresent(book -> stats.put("getMaxPriceBook()", book.toPriceString()));

        getMinBy(Book::getPrice).ifPresent(book -> stats.put("getMinBy(price)", book.toPriceString()));
        getMinPriceBook().ifPresent(book -> stats.put("getMinPriceBook()", book.toPriceString()));

        stats.put("getAveragePrice()", Util.format(getAveragePrice()));
        stats.put("total books count", String.valueOf(sumBooksByQuantity()));
        stats.put("total books value", Util.format(sumBooksByTotalValue()));

        return stats;
    }

    private List<Book> initBooks() {
        List<Book> books = new ArrayList<>();
        List<String> categories = IntStream.range(1, 6).mapToObj(index -> "category " + index).toList();
        List<String> authors = IntStream.range(1, 6).mapToObj(index -> "author " + index).toList();
        IntStream.rangeClosed(1, 20).forEach(index ->
            books.add(new Book(
                    "title " + index,
                    categories.get(Util.getRand(categories.size())),
                    authors.get(Util.getRand(authors.size())),
                    Util.getDouble(50, 500),
                    Util.getRand(100) + 1))
        );
        return books;
    }
}
