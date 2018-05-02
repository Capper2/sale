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
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApplicationSale {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSale.class);

    public static void main(String[] args) throws URISyntaxException, IOException {

        Map<String, String> options = parseCmd(args);

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

    private static Map<String, String> parseCmd(String[] args) {
        Options options = new Options();

        Option officesFilePath = new Option("o", "offices-file-path", true, "src\\main\\resources\\offices.txt");
        officesFilePath.setRequired(true);
        options.addOption(officesFilePath);

        Option operationsFilePath = new Option("of", "operations-file-path", true, "operations.csv");
        operationsFilePath.setRequired(true);
        options.addOption(operationsFilePath);

        Option countOperations = new Option("oc", "operations-count", true, "90000");
        countOperations.setRequired(true);
        options.addOption(countOperations);


        CommandLine cmd = null;
        try {
            cmd = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            new HelpFormatter().printHelp("generator-sale", options);
            System.exit(1);
        }

        Map<String, String> optionsMap = new HashMap<>();

        if (cmd != null) {
            for (Iterator<Option> it = cmd.iterator(); it.hasNext(); ) {
                Option o = it.next();
                optionsMap.put(o.getLongOpt(), o.getValue());
            }
        }

        return optionsMap;
    }
}
