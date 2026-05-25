package Service;

import Model.Product;
import java.math.BigDecimal;
import java.util.List;

public class saleService {

    // 1. Instância única global (Singleton)
    private static saleService instance;

    // Dependência do serviço de produtos para buscar os preços atualizados
    private final productService prodService;

    // 2. Construtor privado
    private saleService() {
        this.prodService = productService.getInstance();
    }

    // 3. Ponto de acesso para os Controllers
    public static synchronized saleService getInstance() {
        if (instance == null) {
            instance = new saleService();
        }
        return instance;
    }

    /**
     * REGRA DE NEGÓCIO ALTERADA:
     * Recebe uma lista de códigos de produtos solicitados pelo usuário,
     * busca os preços na base centralizada e soma tudo.
     */
    public BigDecimal calcularTotalVendaPorCodigos(List<String> codigosProdutos) {
        BigDecimal totalVenda = BigDecimal.ZERO;
        List<Product> produtosCadastrados = prodService.findAll();

        for (String codigo : codigosProdutos) {
            // Procura o produto correspondente ao código digitado
            Product produtoEncontrado = produtosCadastrados.stream()
                    .filter(p -> p.getProductCode().equals(codigo))
                    .findFirst()
                    .orElse(null);

            // Se o produto existir, acumula o preço dele na soma total
            if (produtoEncontrado != null) {
                totalVenda = totalVenda.add(produtoEncontrado.getPrice());
            }
        }

        return totalVenda;
    }
}