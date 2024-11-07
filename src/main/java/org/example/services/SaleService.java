package org.example.services;

import org.example.Util;
import org.example.models.Book;
import org.example.models.Sale;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SaleService {

    private final List<Sale> sales;

    public SaleService(List<Book> books) {
        this.sales = initSales(books);
    }

    public Stream<String> getSoldBooksNamesStream() {
        return sales.stream().flatMap(sale->sale.getSoldBookTitles().stream());
    }

    public long getSolsBooksTotalCount() {
        return sales.stream().mapToInt(sale -> sale.getSoldBookTitles().size()).sum();
        //both returns work
        //return getSoldBooksNamesStream().count();
    }

    public List<Sale> getSales() {
        return sales;
    }

    public Stream<Sale> getSalesStream() {
        return sales.stream();
    }

    private List<Sale> initSales(List<Book> books) {
        List<Sale> sales = new ArrayList<>();

        IntStream.rangeClosed(1, 20).forEach(value -> {
            List<Book> randomBooks = new ArrayList<>();
            int randomInt = Util.getRand(5) + 1;
            IntStream.range(0, randomInt)
                    .forEach(rand -> randomBooks.add(books.get(Util.getRand(books.size()))));
            sales.add(new Sale(
                    Util.between(LocalDate.now().minusYears(1L).withMonth(1)),
                    randomBooks.stream().map(Book::getTitle).toList(),
                    randomBooks.stream().mapToDouble(Book::getPrice).sum()));
        });
        return sales;
    }
}
