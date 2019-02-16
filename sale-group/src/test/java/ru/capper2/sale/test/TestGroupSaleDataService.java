package ru.capper2.sale.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.runner.RunWith;
import ru.capper2.sale.dao.OperationsDao;
import ru.capper2.sale.model.Operation;
import ru.capper2.sale.service.GroupSaleDataService;

@RunWith(JUnitQuickcheck.class)
public class TestGroupSaleDataService {

  @Property
  public void testGetGroupByDateCorrectSum(@InRange(minDouble = 10_000.0, maxDouble = 100_000.0) double sum1,
                                           @InRange(minDouble = 10_000.0, maxDouble = 100_000.0) double sum2) {

    LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
    BigDecimal bigDecimal1 = BigDecimal.valueOf(sum1).setScale(2, RoundingMode.DOWN);
    BigDecimal bigDecimal2 = BigDecimal.valueOf(sum2).setScale(2, RoundingMode.DOWN);


    List<Operation> list = Arrays.asList(
        new Operation(now, "pointOfSale", 0, bigDecimal1),
        new Operation(now, "pointOfSale", 1, bigDecimal2)
    );


    OperationsDao operationsDAO = mock(OperationsDao.class);
    when(operationsDAO.readAll()).thenReturn(list);

    GroupSaleDataService groupSaleDataService = new GroupSaleDataService("test-group-office.txt", "test-group-date.txt", operationsDAO);

    Map<LocalDate, BigDecimal> groupByDate = groupSaleDataService.getGroupByDate();
    Assert.assertEquals(1, groupByDate.size());
    Assert.assertEquals(bigDecimal1.add(bigDecimal2), groupByDate.get(now.toLocalDate()));
  }

  @Property(trials = 1000)
  public void testGetGroupByDateCorrectOrder() {

    LocalDateTime now1 = LocalDateTime.now().withSecond(0).withNano(0);
    LocalDateTime now2 = now1.plusDays(1);
    LocalDateTime now3 = now2.plusDays(1);
    BigDecimal bigDecimal2 = BigDecimal.valueOf(2D).setScale(2, RoundingMode.DOWN);
    BigDecimal bigDecimal1 = BigDecimal.valueOf(1D).setScale(2, RoundingMode.DOWN);
    BigDecimal bigDecimal3 = BigDecimal.valueOf(3D).setScale(2, RoundingMode.DOWN);

    List<Operation> list = Arrays.asList(
        new Operation(now3, "pointOfSale", 2, bigDecimal3),
        new Operation(now1, "pointOfSale", 0, bigDecimal1),
        new Operation(now2, "pointOfSale", 1, bigDecimal2)
    );

    OperationsDao operationsDAO = mock(OperationsDao.class);
    when(operationsDAO.readAll()).thenReturn(list);

    GroupSaleDataService groupSaleDataService = new GroupSaleDataService("test-group-office.txt", "test-group-date.txt", operationsDAO);

    Map<LocalDate, BigDecimal> groupByDate = groupSaleDataService.getGroupByDate();
    Assert.assertEquals(3, groupByDate.size());

    double i = 1D;
    for (Map.Entry<LocalDate, BigDecimal> entry : groupByDate.entrySet()) {
      Assert.assertEquals(BigDecimal.valueOf(i).setScale(2), entry.getValue());
      i++;
    }
  }

  @Property
  public void testGetGroupBySaleOfPlaceCorrectSum(@InRange(minDouble = 10_000.0, maxDouble = 100_000.0) double sum1,
                                                  @InRange(minDouble = 10_000.0, maxDouble = 100_000.0) double sum2) {

    LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
    BigDecimal bigDecimal1 = BigDecimal.valueOf(sum1).setScale(2, RoundingMode.DOWN);
    BigDecimal bigDecimal2 = BigDecimal.valueOf(sum2).setScale(2, RoundingMode.DOWN);


    List<Operation> list = Arrays.asList(
        new Operation(now, "pointOfSale", 0, bigDecimal1),
        new Operation(now, "pointOfSale", 1, bigDecimal2)
    );


    OperationsDao operationsDAO = mock(OperationsDao.class);
    when(operationsDAO.readAll()).thenReturn(list);

    GroupSaleDataService groupSaleDataService = new GroupSaleDataService("test-group-office.txt", "test-group-date.txt", operationsDAO);

    Map<String, BigDecimal> groupBySaleOfPlace = groupSaleDataService.getGroupBySaleOfPlace();
    Assert.assertEquals(1, groupBySaleOfPlace.size());
    Assert.assertEquals(bigDecimal1.add(bigDecimal2), groupBySaleOfPlace.get("pointOfSale"));
  }

  @Property(trials = 1000)
  public void testGetGroupBySaleOfPlaceCorrectOrder() {

    LocalDateTime now1 = LocalDateTime.now().withSecond(0).withNano(0);
    LocalDateTime now2 = now1.plusDays(1);
    LocalDateTime now3 = now2.plusDays(1);
    BigDecimal bigDecimal2 = BigDecimal.valueOf(2D).setScale(2, RoundingMode.DOWN);
    BigDecimal bigDecimal1 = BigDecimal.valueOf(1D).setScale(2, RoundingMode.DOWN);
    BigDecimal bigDecimal3 = BigDecimal.valueOf(3D).setScale(2, RoundingMode.DOWN);

    List<Operation> list = Arrays.asList(
        new Operation(now3, "pointOfSale2", 2, bigDecimal3),
        new Operation(now1, "pointOfSale0", 0, bigDecimal1),
        new Operation(now2, "pointOfSale1", 1, bigDecimal2)
    );

    OperationsDao operationsDAO = mock(OperationsDao.class);
    when(operationsDAO.readAll()).thenReturn(list);

    GroupSaleDataService groupSaleDataService = new GroupSaleDataService("test-group-office.txt", "test-group-date.txt", operationsDAO);

    Map<String, BigDecimal> groupBySaleOfPlace = groupSaleDataService.getGroupBySaleOfPlace();
    Assert.assertEquals(3, groupBySaleOfPlace.size());

    int i = 2;
    for (Map.Entry<String, BigDecimal> entry : groupBySaleOfPlace.entrySet()) {
      Assert.assertEquals("pointOfSale" + i, entry.getKey());
      i--;
    }
  }
}
