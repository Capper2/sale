package ru.capper2.sale.dao;

import java.util.List;
import ru.capper2.sale.model.Operation;

public interface OperationsDao {
  void write(Operation operation);

  void writeAll(List<Operation> operations);

  List<Operation> readAll();
}
