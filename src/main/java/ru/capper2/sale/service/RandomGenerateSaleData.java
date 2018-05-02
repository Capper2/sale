package ru.capper2.sale.service;

import ru.capper2.sale.dao.OperationsDAO;
import ru.capper2.sale.dao.PointOfSaleDAO;
import ru.capper2.sale.model.Operation;

import java.util.ArrayList;
import java.util.List;

public class RandomGenerateSaleData {
    private final RandomDateService randomDateService;
    private final OperationsDAO operations;
    private final PointOfSaleDAO pointOfSale;
    private final RandomSumService randomSumService;

    public RandomGenerateSaleData(RandomDateService randomDateService, OperationsDAO operations, PointOfSaleDAO pointOfSale, RandomSumService randomSumService) {
        this.randomDateService = randomDateService;
        this.operations = operations;
        this.pointOfSale = pointOfSale;
        this.randomSumService = randomSumService;
    }

    public void createSaleDataInFile(long numberOfOperations) {
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
