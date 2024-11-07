package org.example.printers;

import org.example.services.SaleService;
import org.example.services.StatsService;

public class SalesStatsPrinter {
    public static void print(SaleService saleService) {
        System.out.println("\n\n");
        System.out.println("***** All Sales *****");
        saleService.getSales().forEach(System.out::println);
    }

    public static void print(StatsService statsService) {
        System.out.println("\n");
        System.out.println("***** Sales Stats *****");

        statsService.getStats().forEach((stat, map) -> {
            System.out.println("*** By " + stat +" ***");
            map.forEach((key, value) -> System.out.printf("%-25s%15s%n", key, value));
            System.out.println("\n");
        });

    }
}
