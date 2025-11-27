package Repository;

import Model.Budget;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class budgetRepository {
    private final List<Budget> budgets = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);;

    void save(Budget budget){
        if(budget.getId() == 0){
            budget.setId(idCounter.getAndIncrement());
        }
        this.budgets.add(budget);
    }

}
