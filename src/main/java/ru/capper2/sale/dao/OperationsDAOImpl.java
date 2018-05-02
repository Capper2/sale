package ru.capper2.sale.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.capper2.sale.model.Operation;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperationsDAOImpl implements OperationsDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationsDAOImpl.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm");
    private final Path file;

    public OperationsDAOImpl(String fileName) {
        file = Paths.get(fileName);
    }

    @Override
    public void write(Operation operation) {
        StringBuilder sb = new StringBuilder(40);
        sb
                .append(operation.getDateTime().format(FORMATTER)).append(";")
                .append(operation.getSaleOfPoint()).append(";")
                .append(operation.getNumder()).append(";")
                .append(operation.getSum()).append(";");


        try {
            Files.write(file, Collections.singleton(sb.toString()), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void writeAll(List<Operation> operations) {
        List<String> collect = operations.stream()
                .map(operation ->
                        new StringBuilder(40)
                                .append(operation.getDateTime().format(FORMATTER)).append(";")
                                .append(operation.getSaleOfPoint()).append(";")
                                .append(operation.getNumder()).append(";")
                                .append(operation.getSum()).append(";")
                                .toString()
                )
                .collect(Collectors.toList());
        try {
            if (Files.deleteIfExists(file))
                LOGGER.info("File \"{}\" was deleted.", file.toAbsolutePath().toString());
            LOGGER.info("File \"{}\" was created.", file.toAbsolutePath().toString());
            Files.write(file, collect, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Operation> readAll() {
        try (Stream<String> stream = Files.lines(file)) {
            List<Operation> operations = stream
                    .map(s -> {
                        try {
                            String[] split = s.split(";", -1);
                            LocalDateTime dateTime = null;
                            String time;
                            String saleOfPoint = "";
                            long number = 0L;
                            BigDecimal sum = null;
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

            return operations;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
