package ru.capper2.sale;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateService {

    private final int year;
    private final LocalDateTime startDate;
    private final long minutesInYear;

    public RandomDateService(int year) {
        this.year = year;
        startDate = LocalDateTime.of(year, Month.JANUARY, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, Month.DECEMBER, 31, 23, 59);
        minutesInYear = ChronoUnit.MINUTES.between(startDate, endDate);
    }

    public LocalDateTime getRandomDate() {
        return startDate.plusMinutes(ThreadLocalRandom.current().nextLong(minutesInYear));
    }

    public int getYear() {
        return year;
    }
}
