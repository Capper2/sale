package ru.capper2.sale.test;

import org.junit.Test;
import ru.capper2.sale.service.RandomSumService;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class TestRandomSumService {

    @Test
    public void testRangeRandomSumService() {
        BigDecimal min = BigDecimal.valueOf(10_000D);
        BigDecimal max = BigDecimal.valueOf(100_000D);
        RandomSumService randomSumService = new RandomSumService(min, max);
        assertNotNull(randomSumService);

        for (int i = 0; i < 1_000_000; i++) {
            BigDecimal randomSum = randomSumService.getRandomSum();
            assertTrue(randomSum.compareTo(min) >= 0 && randomSum.compareTo(max) < 0);
        }
    }
}
