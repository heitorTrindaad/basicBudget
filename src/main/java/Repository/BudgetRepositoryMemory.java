package Repository;

import Model.Budget;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BudgetRepositoryMemory implements BudgetRepository {
    private final List<Budget> budgets;
    private final AtomicInteger idCounter;

    public BudgetRepositoryMemory() {
        this.budgets = new ArrayList<>(BudgetJsonStorage.loadFromFile());
        int maxId = budgets.stream().mapToInt(Budget::getId).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId + 1);
    }

    @Override
    public void save(Budget budget) {
        budget.setId(idCounter.getAndIncrement());
        budgets.add(budget);
        BudgetJsonStorage.saveToFile(budgets);
    }

    @Override
    public void update(Budget budget) {
        for (int i = 0; i < budgets.size(); i++) {
            if (budgets.get(i).getId() == budget.getId()) {
                budgets.set(i, budget);
                BudgetJsonStorage.saveToFile(budgets);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        budgets.removeIf(b -> b.getId() == id);
        BudgetJsonStorage.saveToFile(budgets);
    }

    @Override
    public List<Budget> findAll() {
        return new ArrayList<>(budgets);
    }

    @Override
    public Budget findById(int id) {
        return budgets.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }
}