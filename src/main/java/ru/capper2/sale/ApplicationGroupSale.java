package ru.capper2.sale;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.capper2.sale.dao.OperationsDAOImpl;
import ru.capper2.sale.service.GroupSaleDataService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApplicationGroupSale {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationGroupSale.class);

    public static void main(String[] args) {

        Map<String, String> options = parseCmd(args);

        LOGGER.info("Application started.");

        GroupSaleDataService groupSaleDataService = new GroupSaleDataService(
                options.get("group-office-file-path"),
                options.get("group-date-file-path"),
                new OperationsDAOImpl(options.get("operations-file-path"))
        );

        groupSaleDataService.saveGroupByDate();
        groupSaleDataService.saveGroupBySaleOfPlace();

        LOGGER.info("Application finished.");
    }

    private static Map<String, String> parseCmd(String[] args) {
        Options options = new Options();

        Option filePathGroupDate = new Option("gd", "group-date-file-path", true, "sums-by-dates.txt");
        filePathGroupDate.setRequired(true);
        options.addOption(filePathGroupDate);

        Option filePathGroupOffice = new Option("go", "group-office-file-path", true, "sums-by-offices.txt");
        filePathGroupOffice.setRequired(true);
        options.addOption(filePathGroupOffice);

        Option filePathOperations = new Option("of", "operations-file-path", true, "operations.csv");
        filePathOperations.setRequired(true);
        options.addOption(filePathOperations);


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




