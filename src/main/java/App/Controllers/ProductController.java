package App.Controllers;

import Model.Product;
import Repository.ProductRepositoryMemory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class ProductController {

    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private TextField txtCodigo;

    @FXML private TableView<Product> tblProduct;
    @FXML private TableColumn<Product, String> colNome;
    @FXML private TableColumn<Product, String> colPreco;
    @FXML private TableColumn<Product, String> colCodigo;

    private final ProductRepositoryMemory repo =
            new ProductRepositoryMemory();

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        atualizarLista();
    }

    @FXML
    public void salvar() {

        Product p = new Product();
        p.setName(txtNome.getText());
        Locale brasil = new Locale("pt", "BR");

        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(brasil));
        df.setParseBigDecimal(true);
        try {
            BigDecimal preco = (BigDecimal) df.parse(txtPreco.getText());
            p.setPrice(preco);
        } catch (ParseException e) {

        }
        p.setProductCode(Integer.parseInt(txtCodigo.getText()));
        limpar();
        repo.save(p);
        atualizarLista();
    }

    @FXML
    public void limpar() {
        txtNome.clear();
        txtPreco.clear();
        txtCodigo.clear();
    }

    private void atualizarLista() {
        tblProduct.getItems().setAll(repo.findAll());
    }
}
