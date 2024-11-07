package org.example;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Random;

public final class Util {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
    private static final Random rand = new Random();

    private Util() {}

    public static String format(Double value) {
        return DECIMAL_FORMAT.format(value);
    }

    /***
     * max exclusive, min 0
     * @param max
     * @return
     */
    public static int getRand(int max) {
        return rand.nextInt(max);
    }

    /***
     * min and max inclusive
     * @param min
     * @param max
     * @return
     */
    public static double getDouble(int min, int max) {
        return rand.nextDouble(max + 1 - min) + min;
    }

    /***
     * startInclusive start Date, endDate is LocalDate.now() exclusive
     * @param startInclusive
     * @return
     */
    public static LocalDate between(LocalDate startInclusive) {
        LocalDate endExclusive = LocalDate.now();
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = rand.nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }
}
