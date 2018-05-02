package ru.capper2.sale.dao;

import ru.capper2.sale.model.Operation;

import java.util.List;

public interface OperationsDAO {
    void write(Operation operation);

    void writeAll(List<Operation> operations);

    List<Operation> readAll();
}
