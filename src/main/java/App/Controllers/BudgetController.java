package App.Controllers;

import Model.*;
import Repository.BudgetRepositoryMemory;
import Repository.ClientRepositoryMemory;
import Repository.ProductRepositoryMemory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BudgetController {

    @FXML private ComboBox<Product> cbProduto;
    @FXML private TextField txtQtd;
    @FXML private TextField txtNomeGrupo;
    @FXML private TreeView<Object> treeOrcamento;
    @FXML private Label lblTotal;
    @FXML private ComboBox<Client> cbClientes;

    private final ClientRepositoryMemory clientRepo = new ClientRepositoryMemory();
    private final ProductRepositoryMemory repo = new ProductRepositoryMemory();
    private final BudgetRepositoryMemory budgetRepo = BudgetRepositoryMemory.getInstance();

    private TreeItem<Object> rootNode = new TreeItem<>("Orçamento");
    private BigDecimal totalGeral = BigDecimal.ZERO;

    @FXML
    public void initialize() {
        cbClientes.getItems().addAll(clientRepo.findAll());
        cbProduto.getItems().setAll(repo.findAll());

        // Configuração da Árvore
        treeOrcamento.setRoot(rootNode);
        treeOrcamento.setShowRoot(false);
    }

    @FXML
    public void criarNovoGrupo() {
        String nome = txtNomeGrupo.getText();
        if (nome != null && !nome.trim().isEmpty()) {
            BudgetSubgroup novoGrupo = new BudgetSubgroup(nome);
            TreeItem<Object> grupoNode = new TreeItem<>(novoGrupo);
            rootNode.getChildren().add(grupoNode);
            treeOrcamento.getSelectionModel().select(grupoNode); // Seleciona automaticamente o novo grupo
            txtNomeGrupo.clear();
        }
    }

    @FXML
    public void add() {
        TreeItem<Object> selecionado = treeOrcamento.getSelectionModel().getSelectedItem();

        // Validação: Precisa selecionar um grupo para adicionar o produto
        if (selecionado == null || !(selecionado.getValue() instanceof BudgetSubgroup)) {
            mostrarAlerta("Selecione um GRUPO na lista para adicionar o produto!");
            return;
        }

        Product p = cbProduto.getValue();
        if (p == null || txtQtd.getText().isEmpty()) {
            mostrarAlerta("Selecione um produto e informe a quantidade.");
            return;
        }

        try {
            BigDecimal qtd = new BigDecimal(txtQtd.getText().replace(",", "."));
            BigDecimal subtotal = p.getPrice().multiply(qtd);

            // 1. Adiciona ao Modelo
            BudgetSubgroup grupo = (BudgetSubgroup) selecionado.getValue();
            BudgetSubgetItem novoItem = new BudgetSubgetItem(p, qtd, subtotal);
            grupo.addItem(novoItem);

            // 2. Adiciona à Interface (Visual)
            TreeItem<Object> itemNode = new TreeItem<>(novoItem);
            selecionado.getChildren().add(itemNode);
            selecionado.setExpanded(true);

            recalcularTotalGeral();
            txtQtd.clear();

        } catch (NumberFormatException e) {
            mostrarAlerta("Quantidade inválida!");
        }
    }

    @FXML
    public void removerSelecionado() {
        TreeItem<Object> selecionado = treeOrcamento.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        TreeItem<Object> pai = selecionado.getParent();

        // Se o que estamos removendo é um item de produto
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

            // Volta os dados para os campos
            cbProduto.setValue(item.getProduct());
            txtQtd.setText(item.getQuantity().toString());

            // Remove o antigo para o usuário "atualizar" ao clicar em add
            removerSelecionado();
        } else {
            mostrarAlerta("Selecione um produto individual para editar.");
        }
    }

    private void recalcularTotalGeral() {
        totalGeral = BigDecimal.ZERO;
        for (TreeItem<Object> grupoNode : rootNode.getChildren()) {
            BudgetSubgroup g = (BudgetSubgroup) grupoNode.getValue();
            totalGeral = totalGeral.add(g.getSubtotal());

            // Gambiarra necessária no JavaFX para atualizar o texto do Grupo na TreeView
            grupoNode.setValue(null);
            grupoNode.setValue(g);
        }
        lblTotal.setText("Total Geral: R$ " + totalGeral);
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

        // Coleta todos os grupos da árvore
        List<BudgetSubgroup> listaFinal = new ArrayList<>();
        for (TreeItem<Object> node : rootNode.getChildren()) {
            listaFinal.add((BudgetSubgroup) node.getValue());
        }
        b.setSubgroups(listaFinal);

        budgetRepo.save(b);

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