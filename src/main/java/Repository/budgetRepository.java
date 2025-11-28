package Repository;

import Model.Budget;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class budgetRepository {
    private final List<Budget> budgets = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);;

    public void save(Budget budget){
        if(budget.getId() == 0){
            budget.setId(idCounter.getAndIncrement());
        }
        this.budgets.add(budget);
    }

    public Budget findById(int id){
        for(Budget budget : budgets){
            if(budget.getId() == id){
                return budgets.get(id);
            }
        }
        return null;
    }

    public void update(int id, Budget budget){
        Budget existstingbudget = findById(id);
        if(existstingbudget != null){
            existstingbudget.setMeasurement(budget.getMeasurement());
            existstingbudget.setQuantity(budget.getQuantity());
        }
    }

    public void remove(int id){
        for (Budget budget : budgets){
            if(budget.getId() == id){
                budgets.remove(budget);
            }
        }
    }

}
