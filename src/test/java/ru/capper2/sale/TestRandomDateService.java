package ru.capper2.sale;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DateTimeException;

import static org.junit.Assert.*;

public class TestRandomDateService {

    @Test
    public void testInitRandomDateServiceMinValue() {
        RandomDateService randomDateService = new RandomDateService(-999_999_999);
        assertNotNull(randomDateService);
    }

    @Test
    public void testInitRandomDateServiceMaxValue() {
        RandomDateService randomDateService = new RandomDateService(999_999_999);
        assertNotNull(randomDateService);
    }

    @Test(expected = DateTimeException.class)
    public void testInitRandomDateServiceMoreThenMaxValue() {
        RandomDateService randomDateService = new RandomDateService(1_000_000_000);
    }

    @Test(expected = DateTimeException.class)
    public void testInitRandomDateServiceLessThenMinValue() {
        RandomDateService randomDateService = new RandomDateService(-1_000_000_000);
    }


}
