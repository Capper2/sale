package ru.capper2.sale.test;

import static org.junit.Assert.assertNotNull;

import java.time.DateTimeException;
import org.junit.Test;
import ru.capper2.sale.service.RandomDateService;

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
