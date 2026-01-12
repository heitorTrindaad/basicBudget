package Repository;

import Model.Product;

import java.util.List;

public interface ProductRepository {
        Product save(Product product);
        Product findById(int id);
        List<Product> findAll();
        void delete(int id);
}
