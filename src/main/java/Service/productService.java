package Service;

import Model.Product;
import Repository.ProductRepositoryMemory;
import java.util.List;

public class productService {

    private static productService instance;
    private final ProductRepositoryMemory repository;

    private productService() {
        this.repository = new ProductRepositoryMemory();
    }

    public static synchronized productService getInstance() {
        if (instance == null) {
            instance = new productService();
        }
        return instance;
    }

    public void save(Product product) {
        repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }
}