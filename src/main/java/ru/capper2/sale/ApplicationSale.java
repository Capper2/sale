package ru.capper2.sale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.capper2.sale.dao.OperationsDAOImpl;
import ru.capper2.sale.dao.PointOfSaleImplInMemory;
import ru.capper2.sale.service.RandomDateService;
import ru.capper2.sale.service.RandomGenerateSaleData;
import ru.capper2.sale.service.RandomSumService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;

public class ApplicationSale {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSale.class);

    public static void main(String[] args) throws URISyntaxException, IOException {

        LOGGER.info("Application started.");

        RandomGenerateSaleData r = new RandomGenerateSaleData(
                new RandomDateService(LocalDate.now().getYear() - 1),
                new OperationsDAOImpl("result.csv"),
                new PointOfSaleImplInMemory(ClassLoader.getSystemResource("offices.txt").toURI()),
                new RandomSumService(BigDecimal.valueOf(10_000D), BigDecimal.valueOf(100_000D))
        );

        r.createSaleDataInFile(90_000);

        LOGGER.info("Application finished.");
    }
}
