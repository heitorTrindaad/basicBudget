package Repository;

import Model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductRepositoryMemory implements ProductRepository {
    private final List<Product> products;
    private final AtomicInteger idCounter;

    public ProductRepositoryMemory() {
        this.products = new ArrayList<>(ProductJsonStorage.loadFromFile());
        int maxId = products.stream().mapToInt(Product::getId).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId + 1);
    }

    @Override
    public void save(Product product) {
        product.setId(idCounter.getAndIncrement());
        products.add(product);
        ProductJsonStorage.saveToFile(products);
    }

    @Override
    public void update(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                ProductJsonStorage.saveToFile(products);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        products.removeIf(b -> b.getId() == id);
        ProductJsonStorage.saveToFile(products);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Product findById(int id) {
        return products.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }
}