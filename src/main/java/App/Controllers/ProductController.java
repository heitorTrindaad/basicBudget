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
import javafx.event.ActionEvent;

public class ProductController {

    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private TextField txtCodigo;

    @FXML private TableView<Product> tblProduct;
    @FXML private TableColumn<Product, String> colNome;
    @FXML private TableColumn<Product, String> colPreco;
    @FXML private TableColumn<Product, String> colCodigo;
    @FXML private TableColumn<Product, String> colMedida;

    @FXML private MenuButton mbMeasurement;

    private String medidaSelecionada = "Unidade";
    private Product produtoSendoEditado = null;

    private final ProductRepositoryMemory repo =
            new ProductRepositoryMemory();

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colMedida.setCellValueFactory(new PropertyValueFactory<>("measurement"));
        configurarMenuButton();
        atualizarLista();
    }

    @FXML
    public void selecionarMedida(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        this.medidaSelecionada = item.getText();

        mbMeasurement.setText(medidaSelecionada);
    }

    private void configurarMenuButton() {
        for (MenuItem item : mbMeasurement.getItems()) {
            item.setOnAction(e -> {
                medidaSelecionada = item.getText();
                mbMeasurement.setText(medidaSelecionada);
            });
        }
    }

    @FXML
    public void selecionarItem() {
        Product p = tblProduct.getSelectionModel().getSelectedItem();
        if (p != null) {
            produtoSendoEditado = p;

            txtNome.setText(p.getName());
            txtPreco.setText(p.getPrice().toString().replace(".", ","));
            txtCodigo.setText(String.valueOf(p.getProductCode()));

            medidaSelecionada = p.getMeasurement();
            mbMeasurement.setText(medidaSelecionada);
        }
    }

    @FXML
    public void salvar() {
        Product p;
        boolean Novo = false;

        if (produtoSendoEditado == null) {
            p = new Product();
            Novo = true;
        } else {
            p = produtoSendoEditado;
        }
        p.setName(txtNome.getText());
        p.setMeasurement(medidaSelecionada);
        p.setProductCode(Integer.parseInt(txtCodigo.getText()));
        try {
            Locale brasil = new Locale("pt", "BR");
            DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(brasil));
            df.setParseBigDecimal(true);
            BigDecimal preco = (BigDecimal) df.parse(txtPreco.getText());
            p.setPrice(preco);
        } catch (ParseException e) {
            p.setPrice(BigDecimal.ZERO);
        }
        if (Novo) {
            repo.save(p);
        }

        limpar();
        atualizarLista();
    }

    @FXML
    public void limpar() {
        txtNome.clear();
        txtPreco.clear();
        txtCodigo.clear();
        medidaSelecionada = "Unidade";
        mbMeasurement.setText("Selecione a Medida");
        produtoSendoEditado = null;
    }

    private void atualizarLista() {
        tblProduct.getItems().setAll(repo.findAll());
    }
}
