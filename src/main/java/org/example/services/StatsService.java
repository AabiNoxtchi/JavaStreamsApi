package org.example.services;

import org.example.Util;
import org.example.models.Book;
import org.example.models.Sale;
import org.example.models.YearMonthRecord;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatsService {

    private final BookService bookService;
    private final SaleService saleService;

    public StatsService(BookService bookService, SaleService saleService) {
        this.bookService = bookService;
        this.saleService = saleService;
    }

    /***
     * this method returns a convenient map for test purposes
     * @return Map<String, Map<?, String>>>
     */
    public Map<String, Map<?, String>> getStats() {
        return  new LinkedHashMap<>() {{
            put("Author", getSalesByBook(Book::getAuthor));
            put("Category", getSalesByBook(Book::getCategory));
            put("Title", getSalesByBook(Book::getTitle));

            put("sale percentages by book sorted by values descending",
                    getBooksSalesPercentagesSortedByValue());
            put("sale percentages by book sorted by values descending with collectAndThen",
                    getBooksSalesPercentagesSortedByValueByCollectAndThen());

            put("Date sorted by Date ascending", getSortedSalesBySale(Sale::getDate));
            put("Year sorted by Year ascending", getSortedSalesBySale(Sale::getYear));
            put("Month sorted by Month ascending", getSortedSalesBySale(Sale::getMonth));
            put("Year-Month sorted by Year-Month ascending", getSortedSalesBySale(Sale::getYearMonth));
            put("Year-Month sorted by YearMonthRecord ascending", getSortedSalesByYearAndMonthRecord());
        }};
    }

    /***
     * this method returns sales statistics, total revenue for each grouped sales - grouped by a String property of Book model
     * @param func the Book function to group sales by
     * @return  Map<String, String> string representation of sales total sums grouped by given function
     */
    public Map<String, String> getSalesByBook(Function<Book, String> func) {
        return saleService.getSoldBooksNamesStream()
                .map(bookService::getBookByTitle)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.groupingBy(func,
                        Collectors.collectingAndThen(
                                Collectors.summingDouble(Book::getPrice),
                                Util::format)));
    }

    /***
     * this method returns sales statistics, total revenue for each grouped sales - grouped by a Comparable
     *  property of Sale model (Date, Month, ...)
     * @param func the Sale functin to group sales by
     * @return Map<?, String> string representation of sales total sums grouped by given function
     *                          the key may be of different types based on return value of the function
     * @param <T> T could be any type that implements Comparable interface so the returned map
     *           could be sortable when we define TreeMap as the implementation
     */
    public <T extends Comparable<T>> Map<?, String> getSortedSalesBySale(Function<Sale, T> func) {
        return saleService.getSalesStream()
                .collect(Collectors.groupingBy(
                                func,
                                TreeMap::new,
                                Collectors.collectingAndThen(
                                        Collectors.summingDouble(Sale::getTotal),
                                        Util::format)));
    }

    /***
     * this method groups sales based on the year and month values of Sale Date property,
     *      encapsulated to record and passed as the returned map key, this record implements Comparable
     *      to be sortable in TreeMap implementation
     * @return Map<?, String> string representation of sales total sums grouped by YearMonthRecord
     *
     */
    public Map<?, String> getSortedSalesByYearAndMonthRecord() {
        return saleService.getSalesStream()
                .collect(Collectors.groupingBy(
                        sale -> new YearMonthRecord(sale.getYear(), sale.getMonth()),
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.summingDouble(Sale::getTotal),
                                Util::format)));
    }

    /***
     * this method groups sales counts by bookTitles and
     *      calculates the percentage of sales count of each group to total sold books count
     *      then sorts the collected data by value :
     *      because the sorting is based on values in this case in second transformation the map is transformed into list
     *      then again transform it into map : LinkedHashMap implementation to preserve the order of insertion
     */
    public Map<String, String> getBooksSalesPercentagesSortedByValue() {
        Map<String, Long> map = saleService.getSoldBooksNamesStream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long total = map.values().stream().mapToLong(Long::longValue).sum();
        return map.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), (entry.getValue()/(double) total) * 100))
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Util.format(entry.getValue()) + "%",
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    /***
     * same method as getBooksSalesPercentagesSortedByValue
     * with the use of collect(Collectors.collectingAndThen to perform the second transformation after the first grouping
     * because the sorting is based on values in this case in second transformation the map is transformed into list
     *  the sorting is applied on values then again transformed into map
     */
    public Map<String, String> getBooksSalesPercentagesSortedByValueByCollectAndThen() {
        long total = saleService.getSolsBooksTotalCount();
        return saleService.getSoldBooksNamesStream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(Function.identity(), Collectors.counting()),
                         map -> map.entrySet().stream()
                                 .map(entry -> Map.entry(entry.getKey(), (entry.getValue() / (double) total) * 100))
                                 .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                 .collect(Collectors.toMap(
                                         Map.Entry::getKey,
                                        entry -> Util.format(entry.getValue()) + "%",
                                        (a, b) -> a,
                                        LinkedHashMap::new))));
    }

}
