package Repository;

import Model.Sale;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SaleRepositoryMemory implements SaleRepository {
    private final List<Sale> sales;
    private final AtomicInteger idCounter;

    public SaleRepositoryMemory() {
        this.sales = new ArrayList<>(SaleJsonStorage.loadFromFile());
        int maxId = sales.stream().mapToInt(Sale::getId).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId + 1);
    }

    @Override
    public void save(Sale Sale) {
        Sale.setId(idCounter.getAndIncrement());
        sales.add(Sale);
        SaleJsonStorage.saveToFile(sales);
    }

    @Override
    public void update(Sale Sale) {
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getId() == Sale.getId()) {
                sales.set(i, Sale);
                SaleJsonStorage.saveToFile(sales);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        sales.removeIf(b -> b.getId() == id);
        SaleJsonStorage.saveToFile(sales);
    }

    @Override
    public List<Sale> findAll() {
        return new ArrayList<>(sales);
    }

    @Override
    public Sale findById(int id) {
        return sales.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }
}