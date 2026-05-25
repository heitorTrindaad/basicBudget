package App.Controllers;

import Model.*;
import Service.budgetService;
import Service.clientService;
import Service.productService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BudgetController {

    @FXML
    private ComboBox<Product> cbProduto;
    @FXML
    private TextField txtQtd;
    @FXML
    private TextField txtNomeGrupo;
    @FXML
    private TextField txtPorcentagemGrupo;

    @FXML
    private TreeTableView<Object> treeOrcamento;

    @FXML
    private Label lblTotal;
    @FXML
    private ComboBox<Client> cbClientes;

    @FXML
    private TreeTableColumn<Object, String> colDescricao;
    @FXML
    private TreeTableColumn<Object, String> colMedida;
    @FXML
    private TreeTableColumn<Object, String> colQtd;
    @FXML
    private TreeTableColumn<Object, String> colValorUnit;
    @FXML
    private TreeTableColumn<Object, String> colValorTotal;

    private final clientService clService = clientService.getInstance();
    private final productService prodService = productService.getInstance();
    private final budgetService budService = budgetService.getInstance();

    private TreeItem<Object> rootNode = new TreeItem<>("Orçamento");
    private BigDecimal totalGeral = BigDecimal.ZERO;

    @FXML
    public void initialize() {
        cbClientes.getItems().addAll(clService.findAll());

        javafx.collections.ObservableList<Product> allProducts = javafx.collections.FXCollections
                .observableArrayList(prodService.findAll());
        FilteredList<Product> filteredProducts = new FilteredList<>(allProducts, p -> true);

        cbProduto.setEditable(true);
        cbProduto.setItems(filteredProducts);
        cbProduto.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            Object selectedObj = cbProduto.getSelectionModel().getSelectedItem();
            if (selectedObj instanceof Product) {
                Product selected = (Product) selectedObj;
                if (selected.getName().equals(newValue))
                    return;
            }

            filteredProducts.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty())
                    return true;
                String filter = newValue.toLowerCase();
                return product.getName().toLowerCase().contains(filter) ||
                        (product.getProductCode() != null && product.getProductCode().toLowerCase().contains(filter));
            });

            if (!newValue.isEmpty())
                cbProduto.show();
        });

        treeOrcamento.setRoot(rootNode);
        treeOrcamento.setShowRoot(false);
        configurarColunas();
    }

    private void configurarColunas() {
        colDescricao.setCellValueFactory(param -> {
            Object obj = param.getValue().getValue();
            if (obj instanceof BudgetSubgroup) {
                BudgetSubgroup g = (BudgetSubgroup) obj;
                return new SimpleStringProperty(g.getName() + " (+" + g.getPercentage() + "%)");
            }
            if (obj instanceof BudgetSubgetItem)
                return new SimpleStringProperty(((BudgetSubgetItem) obj).getProduct().getName());
            return null;
        });

        colMedida.setCellValueFactory(param -> {
            Object obj = param.getValue().getValue();
            if (obj instanceof BudgetSubgetItem)
                return new SimpleStringProperty(((BudgetSubgetItem) obj).getProduct().getMeasurement());
            return new SimpleStringProperty("");
        });

        colQtd.setCellValueFactory(param -> {
            Object obj = param.getValue().getValue();
            if (obj instanceof BudgetSubgetItem)
                return new SimpleStringProperty(((BudgetSubgetItem) obj).getQuantity().toString());
            return new SimpleStringProperty("");
        });

        colValorUnit.setCellValueFactory(param -> {
            Object obj = param.getValue().getValue();
            if (obj instanceof BudgetSubgetItem)
                return new SimpleStringProperty(
                        String.format("R$ %.2f", ((BudgetSubgetItem) obj).getProduct().getPrice()));
            return new SimpleStringProperty("");
        });

        colValorTotal.setCellValueFactory(param -> {
            Object obj = param.getValue().getValue();
            if (obj instanceof BudgetSubgroup)
                return new SimpleStringProperty(String.format("R$ %.2f", ((BudgetSubgroup) obj).getSubtotal()));
            if (obj instanceof BudgetSubgetItem)
                return new SimpleStringProperty(String.format("R$ %.2f", ((BudgetSubgetItem) obj).getSubtotal()));
            return null;
        });
    }

    @FXML
    public void criarNovoGrupo() {
        String nome = txtNomeGrupo.getText();
        String percStr = txtPorcentagemGrupo.getText().replace(",", ".");

        if (nome != null && !nome.trim().isEmpty()) {
            try {
                BigDecimal porcentagem = percStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(percStr);
                BudgetSubgroup novoGrupo = new BudgetSubgroup(nome);
                novoGrupo.setPercentage(porcentagem);

                TreeItem<Object> grupoNode = new TreeItem<>(novoGrupo);
                rootNode.getChildren().add(grupoNode);

                treeOrcamento.getSelectionModel().select(grupoNode);

                txtNomeGrupo.clear();
                txtPorcentagemGrupo.clear();
            } catch (NumberFormatException e) {
                mostrarAlerta("Porcentagem inválida!");
            }
        }
    }

    @FXML
    public void add() {
        TreeItem<Object> selecionado = treeOrcamento.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Selecione um grupo ou um item na tabela!");
            return;
        }

        TreeItem<Object> grupoDestino = null;
        if (selecionado.getValue() instanceof BudgetSubgroup) {
            grupoDestino = selecionado;
        } else if (selecionado.getValue() instanceof BudgetSubgetItem) {
            grupoDestino = selecionado.getParent();
        }

        if (grupoDestino == null || !(grupoDestino.getValue() instanceof BudgetSubgroup)) {
            mostrarAlerta("Selecione um GRUPO na lista para adicionar o produto!");
            return;
        }
        Object selection = cbProduto.getSelectionModel().getSelectedItem();
        Product p = null;

        if (selection instanceof Product) {
            p = (Product) selection;
        } else {
            String textoDigitado = cbProduto.getEditor().getText();
            if (textoDigitado != null && !textoDigitado.isEmpty()) {
                p = cbProduto.getItems().stream()
                        .filter(prod -> prod.getName().equalsIgnoreCase(textoDigitado))
                        .findFirst()
                        .orElse(null);
            }
        }

        if (p == null) {
            mostrarAlerta("Selecione um produto válido da lista.");
            return;
        }

        if (txtQtd.getText().trim().isEmpty()) {
            mostrarAlerta("Informe a quantidade.");
            return;
        }

        try {
            BigDecimal qtd = new BigDecimal(txtQtd.getText().replace(",", "."));
            BigDecimal subtotalItem = p.getPrice().multiply(qtd);

            BudgetSubgroup grupo = (BudgetSubgroup) grupoDestino.getValue();
            BudgetSubgetItem novoItem = new BudgetSubgetItem(p, qtd, subtotalItem);

            grupo.addItem(novoItem);

            TreeItem<Object> itemNode = new TreeItem<>(novoItem);
            grupoDestino.getChildren().add(itemNode);
            grupoDestino.setExpanded(true);

            recalcularTotalGeral();

            cbProduto.getSelectionModel().clearSelection();
            cbProduto.getEditor().clear();
            txtQtd.clear();
            cbProduto.requestFocus();

        } catch (NumberFormatException e) {
            mostrarAlerta("Quantidade inválida!");
        }
    }

    @FXML
    public void removerSelecionado() {
        TreeItem<Object> selecionado = treeOrcamento.getSelectionModel().getSelectedItem();
        if (selecionado == null)
            return;

        TreeItem<Object> pai = selecionado.getParent();
        if (pai == null)
            return;

        if (selecionado.getValue() instanceof BudgetSubgetItem) {
            BudgetSubgroup grupoPai = (BudgetSubgroup) pai.getValue();
            grupoPai.getItems().remove((BudgetSubgetItem) selecionado.getValue());
        }

        pai.getChildren().remove(selecionado);
        recalcularTotalGeral();
    }

    @FXML
    public void editarSelecionado() {
        TreeItem<Object> selecionado = treeOrcamento.getSelectionModel().getSelectedItem();

        if (selecionado != null && selecionado.getValue() instanceof BudgetSubgetItem) {
            BudgetSubgetItem item = (BudgetSubgetItem) selecionado.getValue();
            cbProduto.setValue(item.getProduct());
            txtQtd.setText(item.getQuantity().toString());
            removerSelecionado();
        } else {
            mostrarAlerta("Selecione um produto individual para editar.");
        }
    }

    private void recalcularTotalGeral() {
        totalGeral = BigDecimal.ZERO;
        for (TreeItem<Object> grupoNode : rootNode.getChildren()) {
            if (grupoNode.getValue() instanceof BudgetSubgroup) {
                BudgetSubgroup g = (BudgetSubgroup) grupoNode.getValue();
                totalGeral = totalGeral.add(g.getSubtotal());
            }
        }
        lblTotal.setText(String.format("Total Geral: R$ %.2f", totalGeral));
        treeOrcamento.refresh();
    }

    @FXML
    public void salvar() {
        if (cbClientes.getValue() == null) {
            mostrarAlerta("Selecione um cliente!");
            return;
        }

        Budget b = new Budget();
        b.setClient(cbClientes.getValue());
        b.setTotal(totalGeral);

        List<BudgetSubgroup> listaFinal = new ArrayList<>();
        for (TreeItem<Object> node : rootNode.getChildren()) {
            listaFinal.add((BudgetSubgroup) node.getValue());
        }
        b.setSubgroups(listaFinal);

        budService.save(b);
        mostrarAlerta("Orçamento salvo com sucesso!");
        limparTela();
    }

    private void limparTela() {
        rootNode.getChildren().clear();
        totalGeral = BigDecimal.ZERO;
        lblTotal.setText("Total Geral: R$ 0.00");
        cbClientes.setValue(null);
        cbProduto.setValue(null);
        txtQtd.clear();
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}