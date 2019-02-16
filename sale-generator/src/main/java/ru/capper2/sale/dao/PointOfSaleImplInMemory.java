package ru.capper2.sale.dao;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PointOfSaleImplInMemory implements PointOfSaleDao {
  private final List<String> pointOfSale;

  public PointOfSaleImplInMemory(URI fileName) throws IOException {

    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
      this.pointOfSale = stream.distinct().collect(Collectors.toList());
    }
  }

  @Override
  public String getRandomPointOfSale() {
    return pointOfSale.get(ThreadLocalRandom.current().nextInt(pointOfSale.size()));
  }

  public List<String> getAll() {
    return pointOfSale;
  }
}
