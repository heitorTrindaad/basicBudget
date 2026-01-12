package Repository;

import Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductRepositoryMemory implements ProductRepository {

    private final List<Product> products = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public Product save(Product product){
        if(product.getId() == 0){
            product.setId(idCounter.getAndIncrement());
        }
        products.add(product);
        return product;
    }

    public Product findById(int id){
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Product> findAll(){
        return products;
    }

    public void delete(int id){
        products.removeIf(p -> p.getId() == id);
    }
}

