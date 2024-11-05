package org.example.models;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Sale {

    private final LocalDate date;
    private final double total;
    private final List<String> soldBookNames;

    public Sale(List<String> selectedBookNames, double price) {
        this.date = LocalDate.now();
        this.soldBookNames = selectedBookNames;
        this.total = price;
    }

    public Sale(String date, double total, List<String> soldBookNames) {
        this.date = LocalDate.parse(date);
        this.total = total;
        this.soldBookNames = soldBookNames;
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

    public List<String> getSoldBookNames() {
        return soldBookNames;
    }
}
