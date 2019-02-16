package ru.capper2.sale.test;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.runner.RunWith;
import ru.capper2.sale.service.RandomDateService;

@RunWith(JUnitQuickcheck.class)
public class PBTRandomDateService {

  @Property(trials = 1000)
  public void testInitRandomDateServiceWithRandomYear(@InRange(minInt = -999_999_999, maxInt = 999_999_999) int year) {
    RandomDateService randomDateService = new RandomDateService(year);
    Assert.assertNotNull(randomDateService);

    LocalDateTime randomDate = randomDateService.getRandomDate();
    Assert.assertEquals(year, randomDate.getYear());
  }

}
