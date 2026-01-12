package Repository;

import Model.Budget;

import java.util.List;

public interface BudgetRepository {
    Budget save(Budget budget);
    Budget findById(int id);
    List<Budget> findAll();
    void setAll(List<Budget> list);
    void delete(int id);
}