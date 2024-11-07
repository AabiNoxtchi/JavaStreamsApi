package org.example.models;

import org.example.Util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Sale {

    private final LocalDate date;
    private final double total;
    private final List<String> soldBookTitles;

    public Sale(LocalDate date, List<String> bookTitles, double total) {
        this.date = Objects.isNull(date) ? LocalDate.now() : date;
        this.soldBookTitles = bookTitles;
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public Month getMonth() {
        return date.getMonth();
    }

    public int getYear() {
        return date.getYear();
    }

    public YearMonth getYearMonth() {
        return YearMonth.from(date);
    }

    public List<String> getSoldBookTitles() {
        return soldBookTitles;
    }

    @Override
    public String toString() {
        return String.format("date = %-15s, soldBookTitles = %-60s, total = %10s",
                date,
                soldBookTitles.stream().map(title -> String.format("%-10s", title)).collect(Collectors.joining(", ")),
                Util.format(total));
    }
}
