package Service;

import Model.Budget;
import Repository.BudgetRepositoryMemory;
import java.util.List;

public class budgetService {

    private static budgetService instance;
    private final BudgetRepositoryMemory repository;

    private budgetService() {
        this.repository = new BudgetRepositoryMemory();
    }

    public static synchronized budgetService getInstance() {
        if (instance == null) {
            instance = new budgetService();
        }
        return instance;
    }

    public void save(Budget budget) {
        repository.save(budget);
    }

    public List<Budget> findAll() {
        return repository.findAll();
    }

    public void delete(int id) {
        repository.delete(id);
    }

    // CORRIGIDO: Agora condiz exatamente com o repositório que criamos juntos
    public void update(int id, Budget budget) {
        budget.setId(id); // Garante que o ID da tela está setado no objeto
        repository.update(budget); // <-- Removido o 'id' extra para casar com a assinatura do Repo
    }
}