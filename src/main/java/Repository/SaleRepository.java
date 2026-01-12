package Repository;

import Model.Sale;

import java.util.List;

public interface SaleRepository {
    Sale save(Sale sale);
    Sale findById(int id);
    List<Sale> findAll();
    void delete(int id);
}
