package Repository;

import Model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductRepositoryMemory implements ProductRepository {

    private static final List<Product> products = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    public ProductRepositoryMemory() {
        if (products.isEmpty()) {
            List<Product> loaded = ProductJsonStorage.loadFromFile();
            if (!loaded.isEmpty()) {
                this.setAll(loaded);
            }
        }
    }

    public Product save(Product product){
        if(product.getId() == 0){
            product.setId(idCounter.getAndIncrement());
        }
        products.add(product);
        ProductJsonStorage.saveToFile(products);
        return product;
    }

    public Product findById(int id){
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Product> findAll(){
        return new ArrayList<>(products);
    }

    public void setAll(List<Product> list){
        products.clear();
        products.addAll(list);

        int maiorId = list.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);

        idCounter.set(maiorId + 1);
    }

    public void delete(int id){
        products.removeIf(p -> p.getId() == id);
        ProductJsonStorage.saveToFile(products);
    }

    public void update(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                ProductJsonStorage.saveToFile(products);
                break;
            }
        }
    }
}
