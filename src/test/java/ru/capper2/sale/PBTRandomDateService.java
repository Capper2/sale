package ru.capper2.sale;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnitQuickcheck.class)
public class PBTRandomDateService {

    @Property(trials = 1000)
    public void testInitRandomDateServiceWithRandomYear(@InRange(minInt = -999_999_999, maxInt = 999_999_999) int year) {
        RandomDateService randomDateService = new RandomDateService(year);
        assertNotNull(randomDateService);

        LocalDateTime randomDate = randomDateService.getRandomDate();
        assertEquals(year, randomDate.getYear());
    }

}
