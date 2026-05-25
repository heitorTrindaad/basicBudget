package Service;

import Model.Product;
import java.math.BigDecimal;
import java.util.List;

public class saleService {

    private static saleService instance;
    private final productService prodService;

    private saleService() {
        this.prodService = productService.getInstance();
    }

    public static synchronized saleService getInstance() {
        if (instance == null) {
            instance = new saleService();
        }
        return instance;
    }

    public BigDecimal calcularTotalVendaPorCodigos(List<String> codigosProdutos) {
        BigDecimal totalVenda = BigDecimal.ZERO;
        List<Product> produtosCadastrados = prodService.findAll();

        for (String codigo : codigosProdutos) {
            Product produtoEncontrado = produtosCadastrados.stream()
                    .filter(p -> p.getProductCode().equals(codigo))
                    .findFirst()
                    .orElse(null);

            if (produtoEncontrado != null) {
                totalVenda = totalVenda.add(produtoEncontrado.getPrice());
            }
        }

        return totalVenda;
    }
}