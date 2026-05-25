package Service;

import Model.Product;
import Repository.ProductRepositoryMemory;
import java.util.List;

public class productService {

    // 1. Instância única global
    private static productService instance;

    // 2. Repositório de produtos centralizado
    private final ProductRepositoryMemory repository;

    // 3. Construtor privado
    private productService() {
        this.repository = new ProductRepositoryMemory();
    }

    // 4. Ponto de acesso do Controller
    public static synchronized productService getInstance() {
        if (instance == null) {
            instance = new productService();
        }
        return instance;
    }

    // Repasse de operações para o repositório
    public void save(Product product) {
        repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }
}