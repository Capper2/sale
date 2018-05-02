package ru.capper2.sale.test;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.runner.RunWith;
import ru.capper2.sale.dao.OperationsDAOImpl;
import ru.capper2.sale.model.Operation;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RunWith(JUnitQuickcheck.class)
public class PBTOperationsDao {
    private final static String FILE_NAME = "result-test.csv";

    @Property(trials = 50)
    public void testReadAll(@From(SaleOfPlaceGenerator.class) String saleOfPoint, long l, double d) {
        OperationsDAOImpl operationsDAO = new OperationsDAOImpl(FILE_NAME);

        Operation operation = new Operation(
                LocalDateTime.now(),
                saleOfPoint,
                l,
                new BigDecimal(d)
        );

        operationsDAO.writeAll(Collections.singletonList(operation));

        List<Operation> operations = operationsDAO.readAll();
        Assert.assertNotNull(operations);
        Assert.assertTrue(operations.size() == 1);
        Assert.assertEquals(operation, operations.get(0));
    }

    @Property(trials = 50)
    public void testWriteLine(@From(SaleOfPlaceGenerator.class) String saleOfPoint, long l, double d) throws IOException {

        OperationsDAOImpl operationsDAO = new OperationsDAOImpl(FILE_NAME);

        List<Operation> operationsOld = operationsDAO.readAll();

        Operation operation = new Operation(
                LocalDateTime.now(),
                saleOfPoint,
                l,
                new BigDecimal(d)
        );

        operationsDAO.write(operation);

        List<Operation> operationsNew = operationsDAO.readAll();
        Assert.assertNotNull(operationsNew);
        if (operationsOld == null || operationsOld.size() == 0)
            Assert.assertTrue(operationsNew.size() == 1);
        else
            Assert.assertTrue(operationsNew.size() == operationsOld.size() + 1);

        Assert.assertTrue(operationsNew.contains(operation));
    }

    @AfterClass
    public static void destroy() throws IOException {
        Path file = Paths.get(FILE_NAME);
        Files.deleteIfExists(file);
    }
}
