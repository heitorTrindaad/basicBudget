package App.Controllers;

import Model.Client;
import Model.Product;
import Repository.ClientRepositoryMemory;
import Repository.ProductRepositoryMemory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;

public class BudgetController {

    @FXML private ComboBox<Product> cbProduto;
    @FXML private TextField txtQtd;
    @FXML private ListView<String> lista;
    @FXML private Label lblTotal;
    @FXML
    private ComboBox<Client> cbClientes;

    private final ClientRepositoryMemory clientRepo =
            new ClientRepositoryMemory();

    private final ProductRepositoryMemory repo =
            new ProductRepositoryMemory();

    private BigDecimal total = BigDecimal.ZERO;

    @FXML
    public void initialize() {
        cbClientes.getItems().addAll(
                clientRepo.findAll()
        );

        cbProduto.getItems().setAll(repo.findAll());
    }

    @FXML
    public void add() {
        Product p = cbProduto.getValue();

        // 1. Troque de int para BigDecimal para aceitar decimais (ex: 1.5)
        BigDecimal qtd = new BigDecimal(txtQtd.getText().replace(",", "."));

        // 2. O cálculo do subtotal continua o mesmo (BigDecimal x BigDecimal)
        BigDecimal subtotal = p.getPrice().multiply(qtd);

        // 3. Adiciona na lista (formate se desejar mais detalhes)
        lista.getItems().add(
                p.getName() + " x" + qtd + " = " + subtotal);

        // 4. Atualiza o total acumulado
        total = total.add(subtotal);
        lblTotal.setText("Total: R$ " + total);
    }

    @FXML
    public void salvar() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Orçamento");
        a.setHeaderText(null);
        a.setContentText(
                "Orçamento salvo!\nTotal: R$ " + total);
        a.show();
    }
}
