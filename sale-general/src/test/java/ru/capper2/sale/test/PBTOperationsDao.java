package ru.capper2.sale.test;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.runner.RunWith;
import ru.capper2.sale.dao.OperationsDaoImpl;
import ru.capper2.sale.model.Operation;

@RunWith(JUnitQuickcheck.class)
public class PBTOperationsDao {
  private final static String FILE_NAME = "result-test.csv";

  @AfterClass
  public static void destroy() throws IOException {
    Path file = Paths.get(FILE_NAME);
    Files.deleteIfExists(file);
  }

  @Property(trials = 50)
  public void testReadAll(@From(SaleOfPlaceGenerator.class) String saleOfPoint, long l, double d) {
    OperationsDaoImpl operationsDAO = new OperationsDaoImpl(FILE_NAME);

    Operation operation = new Operation(
        LocalDateTime.now(),
        saleOfPoint,
        l,
        new BigDecimal(d)
    );

    operationsDAO.writeAll(Collections.singletonList(operation));

    List<Operation> operations = operationsDAO.readAll();
    Assert.assertNotNull(operations);
    Assert.assertEquals(1, operations.size());
    Assert.assertEquals(operation, operations.get(0));
  }

  @Property(trials = 50)
  public void testWriteLine(@From(SaleOfPlaceGenerator.class) String saleOfPoint, long l, double d) throws IOException {

    OperationsDaoImpl operationsDAO = new OperationsDaoImpl(FILE_NAME);

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
    if (operationsOld == null || operationsOld.size() == 0) {
      Assert.assertEquals(1, operationsNew.size());
    } else {
      Assert.assertEquals(operationsNew.size(), operationsOld.size() + 1);
    }

    Assert.assertTrue(operationsNew.contains(operation));
  }
}
