package App.Controllers;

import Model.Budget;
import Model.Client;
import Model.Product;
import Repository.BudgetRepositoryMemory;
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

    private final BudgetRepositoryMemory budgetRepo =
            BudgetRepositoryMemory.getInstance();

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

        BigDecimal qtd = new BigDecimal(txtQtd.getText().replace(",", "."));

        BigDecimal subtotal = p.getPrice().multiply(qtd);

        lista.getItems().add(
                p.getName() + " x" + qtd + " = " + subtotal);

        total = total.add(subtotal);
        lblTotal.setText("Total: R$ " + total);
    }

    @FXML
    public void salvar() {

        Budget b = new Budget();

        b.setClient(cbClientes.getValue());
        b.setTotal(total);

        budgetRepo.save(b);

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Orçamento");
        a.setHeaderText(null);
        a.setContentText(
                "Orçamento salvo!\nTotal: R$ " + total);
        a.show();

        // limpa tela
        lista.getItems().clear();
        total = BigDecimal.ZERO;
        lblTotal.setText("Total: R$ 0.00");
    }
}
