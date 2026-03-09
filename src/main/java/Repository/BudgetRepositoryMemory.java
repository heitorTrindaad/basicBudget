package Repository;

import Model.Budget;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BudgetRepositoryMemory implements BudgetRepository {

    private static BudgetRepositoryMemory instance;

    private final List<Budget> budgets = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    private BudgetRepositoryMemory(){
        if (budgets.isEmpty()) {
            List<Budget> loaded = BudgetJsonStorage.loadFromFile();
            if (!loaded.isEmpty()) {
                this.setAll(loaded);
            }
        }
    }

    public static BudgetRepositoryMemory getInstance(){
        if(instance == null){
            instance = new BudgetRepositoryMemory();
        }
        return instance;
    }

    public Budget save(Budget budget){
        if(budget.getId() == 0){
            budget.setId(idCounter.getAndIncrement());
        }
        budgets.add(budget);
        BudgetJsonStorage.saveToFile(budgets);
        return budget;
    }

    public Budget findById(int id){
        return budgets.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Budget> findAll(){
        return budgets;
    }

    public void setAll(List<Budget> list){
        budgets.clear();
        budgets.addAll(list);

        int maiorId = list.stream()
                .mapToInt(Budget::getId)
                .max()
                .orElse(0);

        idCounter.set(maiorId + 1);
    }

    public void update(int id, Budget updatedBudget) {
        for (int i = 0; i < budgets.size(); i++) {
            if (budgets.get(i).getId() == id) {
                budgets.set(i, updatedBudget);
                BudgetJsonStorage.saveToFile(budgets);
                return;
            }
        }
    }

    public void delete(int id){
        budgets.removeIf(b -> b.getId() == id);
        BudgetJsonStorage.saveToFile(budgets);
    }
}
