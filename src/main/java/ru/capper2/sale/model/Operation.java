package ru.capper2.sale.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Operation {
    private final LocalDateTime dateTime;
    private final String saleOfPoint;
    private final long numder;
    private final BigDecimal sum;

    public Operation(LocalDateTime dateTime, String saleOfPoint, long numder, BigDecimal sum) {
        this.dateTime = dateTime.withSecond(0).withNano(0);
        this.saleOfPoint = saleOfPoint;
        this.numder = numder;
        this.sum = sum.setScale(2, BigDecimal.ROUND_DOWN);
        ;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getSaleOfPoint() {
        return saleOfPoint;
    }

    public long getNumder() {
        return numder;
    }

    public BigDecimal getSum() {
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return numder == operation.numder &&
                Objects.equals(saleOfPoint, operation.saleOfPoint) &&
                Objects.equals(dateTime, operation.dateTime) &&
                Objects.equals(sum, operation.sum);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateTime, saleOfPoint, numder, sum);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "dateTime=" + dateTime +
                ", saleOfPoint='" + saleOfPoint + '\'' +
                ", numder=" + numder +
                ", sum=" + sum +
                '}';
    }
}
