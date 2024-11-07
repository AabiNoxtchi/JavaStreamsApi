package org.example.models;

import java.time.Month;

public record YearMonthRecord(int year, Month month) implements Comparable<YearMonthRecord> {

    @Override
    public int compareTo(YearMonthRecord other) {
        int result = Integer.compare(this.year, other.year);
        return result != 0 ? result :
                this.month.compareTo(other.month);
    }

    @Override
    public String toString() {
        return this.year + "-" + this.month;
    }
}
