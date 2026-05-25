package Repository;

import Model.Sale;
import java.util.List;

public interface SaleRepository {
    void save(Sale sale);

    void update(Sale sale);

    void delete(int id);

    List<Sale> findAll();

    Sale findById(int id);
}