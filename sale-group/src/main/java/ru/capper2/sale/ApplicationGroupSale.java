package ru.capper2.sale;

import java.util.Arrays;
import java.util.Map;
import org.apache.commons.cli.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.capper2.sale.dao.OperationsDaoImpl;
import ru.capper2.sale.service.GroupSaleDataService;

public class ApplicationGroupSale {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationGroupSale.class);

  public static void main(String[] args) {

    Map<String, String> options = ParserCmdOptions.parseCmd(args, Arrays.asList(
        new Option("gd", "group-date-file-path", true, "sums-by-dates.txt"),
        new Option("go", "group-office-file-path", true, "sums-by-offices.txt"),
        new Option("of", "operations-file-path", true, "operations.csv"))
    );

    LOGGER.info("Application started.");

    GroupSaleDataService groupSaleDataService = new GroupSaleDataService(
        options.get("group-office-file-path"),
        options.get("group-date-file-path"),
        new OperationsDaoImpl(options.get("operations-file-path"))
    );

    groupSaleDataService.saveGroupByDate();
    groupSaleDataService.saveGroupBySaleOfPlace();

    LOGGER.info("Application finished.");
  }
}
