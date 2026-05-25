package Repository;

import Model.Budget;
import java.util.List;

public interface BudgetRepository {
    void save(Budget budget);

    void update(Budget budget);

    void delete(int id);

    List<Budget> findAll();

    Budget findById(int id);
}