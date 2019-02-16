package ru.capper2.sale.service;

import java.util.ArrayList;
import java.util.List;
import ru.capper2.sale.dao.OperationsDao;
import ru.capper2.sale.dao.PointOfSaleDao;
import ru.capper2.sale.model.Operation;

public class RandomGenerateSaleData {
  private final RandomDateService randomDateService;
  private final OperationsDao operations;
  private final PointOfSaleDao pointOfSale;
  private final RandomSumService randomSumService;

  public RandomGenerateSaleData(
      RandomDateService randomDateService,
      OperationsDao operations,
      PointOfSaleDao pointOfSale,
      RandomSumService randomSumService
  ) {
    this.randomDateService = randomDateService;
    this.operations = operations;
    this.pointOfSale = pointOfSale;
    this.randomSumService = randomSumService;
  }

  public void createRandomDataAndSaveInFile(long numberOfOperations) {
    List<Operation> list = new ArrayList<>();
    for (int i = 0; i < numberOfOperations; i++) {
      Operation operation = new Operation(
          randomDateService.getRandomDate(),
          pointOfSale.getRandomPointOfSale(),
          i,
          randomSumService.getRandomSum()
      );
      list.add(operation);
    }
    operations.writeAll(list);
  }
}
