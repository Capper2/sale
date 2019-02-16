package ru.capper2.sale.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.capper2.sale.model.Operation;

public class OperationsDaoImpl extends FileDao implements OperationsDao {
  private static final Logger LOGGER = LoggerFactory.getLogger(OperationsDaoImpl.class);
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm");
  private final Path file;

  public OperationsDaoImpl(String fileName) {
    file = Paths.get(fileName);
  }

  @Override
  public void write(Operation operation) {
    String line = operation.getDateTime().format(FORMATTER) + ";"
        + operation.getSaleOfPoint() + ";"
        + operation.getNumder() + ";"
        + operation.getSum() + ";";
    super.saveStringToFile(file, line);
  }

  @Override
  public void writeAll(List<Operation> operations) {
    List<String> collect = operations.stream()
        .map(operation ->
            operation.getDateTime().format(FORMATTER) + ";"
                + operation.getSaleOfPoint() + ";"
                + operation.getNumder() + ";"
                + operation.getSum() + ";"
        )
        .collect(Collectors.toList());
    super.saveCollectionToFile(file, collect);
  }

  @Override
  public List<Operation> readAll() {
    try (Stream<String> stream = Files.lines(file)) {
      return stream
          .map(s -> {
            try {
              final String[] split = s.split(";", -1);
              final LocalDateTime dateTime;
              final String saleOfPoint;
              final long number;
              BigDecimal sum;
              if (split.length == 6) {
                dateTime = LocalDateTime.parse(split[0] + ";" + split[1], FORMATTER);
                saleOfPoint = split[2];
                number = Long.valueOf(split[3]);
                sum = BigDecimal.valueOf(Double.valueOf(split[4]));
                return new Operation(
                    dateTime,
                    saleOfPoint,
                    number,
                    sum
                );
              } else {
                LOGGER.warn("Can not parse string \"{}\".", s);
                return null;
              }
            } catch (Exception e) {
              LOGGER.warn("Can not parse string \"{}\".", s);
              return null;
            }

          })
          .collect(Collectors.toList());
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
      return Collections.emptyList();
    }
  }
}
