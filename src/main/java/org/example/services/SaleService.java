package org.example.services;

import org.example.models.Sale;

import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;

public class SaleService {

    private final List<Sale> sales;

    public SaleService(List<Sale> sales) {
        this.sales = sales;
    }

    public Stream<String> getSoldBooksNamesStream() {
        return sales.stream().flatMap(sale->sale.getSoldBookNames().stream());
    }

    public Stream<Sale> getSalesStream() {
        return sales.stream();
    }

    public double sumSalesTotal() {
        return sumSalesTotal(sales);
    }

    public double sumSalesTotal(List<Sale> sales) {
        return sales.stream().mapToDouble(Sale::getTotal).sum();
    }

    public double sumSalesBy(List<Sale> sales, ToDoubleFunction<? super Sale> func) {
        return sales.stream().mapToDouble(func).sum();
    }
}
