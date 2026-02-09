package Repository;

import Model.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SaleRepositoryMemory implements SaleRepository {

    private final List<Sale> sales = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public Sale save(Sale sale){
        if(sale.getId() == 0){
            sale.setId(idCounter.getAndIncrement());
        }
        sales.add(sale);
        return sale;
    }

    public Sale findById(int id){
        return sales.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Sale> findAll(){
        return sales;
    }

    public void setAll(List<Sale> list){
        sales.clear();
        sales.addAll(list);

        int maiorId = list.stream()
                .mapToInt(Sale::getId)
                .max()
                .orElse(0);

        idCounter.set(maiorId + 1);
    }


    public void delete(int id){
        sales.removeIf(s -> s.getId() == id);
    }
}