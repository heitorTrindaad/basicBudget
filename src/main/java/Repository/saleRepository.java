package Repository;

import Model.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class saleRepository {

    private final List<Sale> sales = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);;

    public void save(Sale sale){
        if(sale.getId() == 0){
            sale.setId(idCounter.getAndIncrement());
        }
        this.sales.add(sale);
    }

    public Sale findById(int id){
        for(Sale sale : sales){
            if(sale.getId() == id){
                return sales.get(id);
            }
        }
        return null;
    }

    public void update(int id, Sale sale){
        Sale existstingSale = findById(id);
        if(existstingSale != null){
            existstingSale.setDate(sale.getDate());
            existstingSale.setTotalAmount(sale.getTotalAmount());
        }
    }

    public void remove(int id){
            Sale sale = findById(id);
            if(sale != null){
                sales.remove(sale);
            }

    }

}
