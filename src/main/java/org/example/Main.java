package org.example;

import org.example.printers.BooksStatsPrinter;
import org.example.printers.SalesStatsPrinter;
import org.example.services.BookService;
import org.example.services.SaleService;
import org.example.services.StatsService;

public class Main {
    public static void main(String[] args) {

        BookService bookService = new BookService();
        BooksStatsPrinter.print(bookService);

        SaleService saleService = new SaleService(bookService.getAllBooks());
        SalesStatsPrinter.print(saleService);

        StatsService statsService = new StatsService(bookService, saleService);
        SalesStatsPrinter.print(statsService);
    }
}