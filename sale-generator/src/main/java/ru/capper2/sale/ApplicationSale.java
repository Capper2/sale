package ru.capper2.sale;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.capper2.sale.dao.OperationsDAOImpl;
import ru.capper2.sale.dao.PointOfSaleImplInMemory;
import ru.capper2.sale.service.RandomDateService;
import ru.capper2.sale.service.RandomGenerateSaleData;
import ru.capper2.sale.service.RandomSumService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApplicationSale {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSale.class);

    public static void main(String[] args) throws IOException {

        Map<String, String> options = ParserCmdOptions.parseCmd(args, Arrays.asList(
                new Option("o", "offices-file-path", true, "src\\main\\resources\\offices.txt"),
                new Option("of", "operations-file-path", true, "operations.csv"),
                new Option("oc", "operations-count", true, "90000"))
        );

        LOGGER.info("Application started.");

        RandomGenerateSaleData r = new RandomGenerateSaleData(
                new RandomDateService(LocalDate.now().getYear() - 1),
                new OperationsDAOImpl(options.get("operations-file-path")),
                new PointOfSaleImplInMemory(new File(options.get("offices-file-path")).toURI()),
                new RandomSumService(BigDecimal.valueOf(10_000D), BigDecimal.valueOf(100_000D))
        );

        long count = 0;
        try {
            count = Long.valueOf(options.get("operations-count"));
        } catch (NumberFormatException nfe) {
            LOGGER.error("Argument \"operations-count\" must be a number.");
            System.exit(1);
        }
        r.createSaleDataInFile(count);

        LOGGER.info("Application finished.");
    }
}
