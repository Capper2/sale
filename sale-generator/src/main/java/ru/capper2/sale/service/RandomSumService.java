package ru.capper2.sale.service;

import java.math.BigDecimal;

public class RandomSumService {
  private final BigDecimal minValue;
  private final BigDecimal between;

  /**
   * Service a pseudorandom {@code BigDecimal} value between minValue (inclusive)
   * and maxValue (exclusive).
   *
   * @param minValue the lower bound (inclusive).
   * @param maxValue the upper bound (exclusive). Must be greater than the minValue.
   * @throws IllegalArgumentException if {@code maxValue} is less or equal minValue.
   */
  public RandomSumService(BigDecimal minValue, BigDecimal maxValue) {
    if (maxValue.compareTo(minValue) <= 0) {
      throw new IllegalArgumentException("The maxValue must be greater than the minValue.");
    }
    this.minValue = minValue;
    this.between = maxValue.subtract(minValue);
  }

  /**
   * Returns a pseudorandom {@code BigDecimal} value between minValue (inclusive)
   * and maxValue (exclusive).
   */
  public BigDecimal getRandomSum() {
    BigDecimal randFromDouble = BigDecimal.valueOf(Math.random());
    BigDecimal actualRandomDec = randFromDouble.multiply(between);
    return actualRandomDec.add(minValue).setScale(2, BigDecimal.ROUND_DOWN);
  }
}
