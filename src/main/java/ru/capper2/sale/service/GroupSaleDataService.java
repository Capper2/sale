package ru.capper2.sale.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.capper2.sale.dao.FileDao;
import ru.capper2.sale.dao.OperationsDAO;
import ru.capper2.sale.model.Operation;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class GroupSaleDataService extends FileDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupSaleDataService.class);
    private final Path fileGroupBySale;
    private final Path fileGroupByDate;

    private final OperationsDAO operationsDAO;

    public GroupSaleDataService(String fileNameGroupBySale, String fileNameGroupByDate, OperationsDAO operationsDAO) {
        this.fileGroupBySale = Paths.get(fileNameGroupBySale);
        this.fileGroupByDate = Paths.get(fileNameGroupByDate);
        this.operationsDAO = operationsDAO;
    }

    public LinkedHashMap<String, BigDecimal> getGroupBySaleOfPlace() {
        List<Operation> operations = operationsDAO.readAll();

        if (operations != null && !operations.isEmpty())
            return operations
                    .stream()
                    .collect(groupingBy(Operation::getSaleOfPoint, new OperationSummaryCollector()))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(toMap(Map.Entry::getKey, e -> e.getValue().sum, (e1, e2) -> e2, LinkedHashMap::new));
        else
            return null;
    }

    public void saveGroupBySaleOfPlace() {
        List<String> collect = getGroupBySaleOfPlace().entrySet().stream()
                .map(entry -> entry.getKey() + ";" + entry.getValue() + ";")
                .collect(Collectors.toList());

        super.saveCollectionToFile(fileGroupBySale, collect);
    }


    public LinkedHashMap<LocalDate, BigDecimal> getGroupByDate() {
        List<Operation> operations = operationsDAO.readAll();

        if (operations != null && !operations.isEmpty())
            return operations
                    .stream()
                    .collect(groupingBy(o -> o.getDateTime().toLocalDate(), new OperationSummaryCollector()))
                    .entrySet()
                    .stream()
                    .sorted(comparingByKey())
                    .collect(toMap(Map.Entry::getKey, e -> e.getValue().sum, (e1, e2) -> e2, LinkedHashMap::new));
        else
            return null;
    }

    public void saveGroupByDate() {
        List<String> collect = getGroupByDate()
                .entrySet()
                .stream()
                .map(entry -> String.valueOf(entry.getKey()) + ";" + entry.getValue() + ";")
                .collect(Collectors.toList());
        super.saveCollectionToFile(fileGroupByDate, collect);
    }

    private static final class Summary implements Comparable<Summary> {


        private BigDecimal sum;

        Summary() {
            this.sum = BigDecimal.ZERO;
        }

        @Override
        public String toString() {
            return "Summary{" +
                    "sum=" + sum +
                    '}';
        }

        @Override
        public int compareTo(Summary o) {
            return sum.compareTo(o.sum);
        }
    }

    private static final class OperationSummaryCollector implements Collector<Operation, Summary, Summary> {

        @Override
        public Supplier<Summary> supplier() {
            return Summary::new;
        }

        @Override
        public BiConsumer<Summary, Operation> accumulator() {
            return (summary, operation) -> {
                summary.sum = summary.sum.add(operation.getSum());
            };
        }

        @Override
        public BinaryOperator<Summary> combiner() {
            return (summary, summary1) -> {
                summary.sum = summary.sum.add(summary1.sum);
                return summary;
            };
        }

        @Override
        public Function<Summary, Summary> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Collector.Characteristics.IDENTITY_FINISH);
        }
    }
}
