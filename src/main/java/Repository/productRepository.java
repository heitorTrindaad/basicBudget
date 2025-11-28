package Repository;

import Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class productRepository {
    private final List<Product> products = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);;

    public void save(Product product){
        if(product.getId() == 0){
            product.setId(idCounter.getAndIncrement());
        }
        this.products.add(product);
    }

    public Product findById(int id){
        for(Product product : products){
            if(product.getId() == id){
                return products.get(id);
            }
        }
        return null;
    }

    public void update(int id, Product product){
        Product existstingProduct = findById(id);
        if(existstingProduct != null){
            existstingProduct.setName(product.getName());
            existstingProduct.setPrice(product.getPrice());
        }
    }

    public void remove(int id){
        Product product = findById(id);
        if(product != null){
            products.remove(product);
        }

    }


}
