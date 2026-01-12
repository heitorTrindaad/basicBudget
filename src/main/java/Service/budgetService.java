package Service;

import Model.Budget;
import Repository.BudgetRepositoryMemory;

import java.math.BigDecimal;

public class budgetService {
    private final BudgetRepositoryMemory budgetRepository;

    public budgetService(BudgetRepositoryMemory budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget createBudget(BigDecimal quantity, String measurement){
        if (measurement==null || measurement.isBlank()){
            throw new IllegalArgumentException("Measurement wasnt filled.");
        } //hi
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Product quantity is invalid.");
        }
        Budget budget = new Budget(quantity, measurement);
        budget.setMeasurement(measurement.trim());
        budget.setQuantity(quantity);

        return budgetRepository.save(budget);
    }

    public Budget updateBudget(int id, BigDecimal quantity, String measurement){
        Budget budget = budgetRepository.findById(id);

        if (budget == null) {
            throw new IllegalArgumentException("Budget not found.");
        }

        if (measurement == null || measurement.isBlank()){
            throw new IllegalArgumentException("Measurement wasnt filled.");
        }

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Product quantity is invalid.");
        }

        budget.setMeasurement(measurement.trim());
        budget.setQuantity(quantity);

        return budgetRepository.save(budget);
    }

    public void deleteClient(int id){
        budgetRepository.delete(id);
    }
}
