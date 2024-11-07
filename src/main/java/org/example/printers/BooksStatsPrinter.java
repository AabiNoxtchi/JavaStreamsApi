package org.example.printers;

import org.example.models.Book;
import org.example.services.BookService;

import java.util.List;
import java.util.Map;

public class BooksStatsPrinter {
    public static void print(BookService bookService) {

        System.out.println("\n\n");
        System.out.println("***** All books *****");
        bookService.getAllBooks().forEach(System.out::println);

        System.out.println("\n");
        System.out.println("*** getBooksBy(List<String> bookTitles)");
        bookService.getBooksBy(List.of("title 1", "title 33")) //one present book title and one doesn't exist
                .forEach(System.out::println);

        System.out.println("\n");
        System.out.println("*** getBooksBy(search : category 5)");
        bookService.getBooksBy("category 5")
                .forEach(System.out::println);

        System.out.println("\n");
        System.out.println("*** getBooksBy(search : 5)");
        bookService.getBooksBy("5")
                .forEach(System.out::println);

        System.out.println("\n");
        Map<Boolean, List<Book>> partitioned = bookService.partitionBooksBy(book -> book.getPrice() <= 100);
        System.out.println("*** partitionBooksBy(book -> book.getPrice() <= 100)");

        System.out.println("** books which meet the criteria ");
        partitioned.get(true).forEach(System.out::println);

        System.out.println("** books which doesn't meet the criteria : ");
        partitioned.get(false).forEach(System.out::println);

        System.out.println("\n");
        Map<Boolean, List<String>> partitionedTitles =
                bookService.partitionBookTitlesBy(book -> book.getQuantity() > 50 && book.getPrice() < 100);
        System.out.println("*** partitionBookTitlesBy(book.getQuantity() > 50 && book.getPrice() < 100)");

        System.out.println("** book titles which meet the criteria");
        partitionedTitles.get(true).forEach(System.out::println);

        System.out.println("** book titles which doesn't meet the criteria ");
        partitionedTitles.get(false).forEach(System.out::println);

        System.out.println("\n");
        Map<Boolean, Integer> partitionedCounts =
                bookService.partitionBookCountsBy(book -> book.getCategory().equals("category 2"));
        System.out.println("*** partitionBookCountsBy(book -> book.getCategory().equals(\"category 2\")");

        System.out.println("** books count which meet the criteria");
        System.out.println(partitionedCounts.get(true));

        System.out.println("** books count which doesn't meet the criteria ");
        System.out.println(partitionedCounts.get(false));

        System.out.println("\n");
        System.out.println("*** books stats : ");
        bookService.getBooksStats().forEach((key, value) -> System.out.printf("%-25s%s%n", key, value));
    }
}
