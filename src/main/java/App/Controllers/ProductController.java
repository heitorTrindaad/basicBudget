package App.Controllers;

import Model.Product;
import Repository.ProductRepositoryMemory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;

public class ProductController {

    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private ListView<Product> lista;

    private final ProductRepositoryMemory repo =
            new ProductRepositoryMemory();

    @FXML
    public void initialize() {
        atualizarLista();
    }

    @FXML
    public void salvar() {

        Product p = new Product();
        p.setName(txtNome.getText());

        BigDecimal preco =
                new BigDecimal(txtPreco.getText());
        p.setPrice(preco);

        repo.save(p);


        txtNome.clear();
        txtPreco.clear();
        atualizarLista();
    }

    private void atualizarLista() {
        lista.getItems().setAll(repo.findAll());
    }
}
