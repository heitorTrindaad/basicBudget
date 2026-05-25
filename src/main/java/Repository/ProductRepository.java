package Repository;

import Model.Product;
import java.util.List;

public interface ProductRepository {
        void save(Product product);

        void update(Product product);

        void delete(int id);

        List<Product> findAll();

        Product findById(int id);
}