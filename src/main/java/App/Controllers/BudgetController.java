package App.Controllers;

import Model.Budget;
import Model.BudgetSubgroup;
import Model.Client;
import Model.Product;
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
    @FXML private TextField txtNomeGrupo; // Campo para nomear o novo grupo
    @FXML private ListView<BudgetSubgroup> listaGrupos; // Mostra os grupos e seus subtotais
    @FXML private Label lblTotal;
    @FXML private ComboBox<Client> cbClientes;


    private final ClientRepositoryMemory clientRepo = new ClientRepositoryMemory();
    private final ProductRepositoryMemory repo = new ProductRepositoryMemory();
    private final BudgetRepositoryMemory budgetRepo = BudgetRepositoryMemory.getInstance();

    private BigDecimal totalGeral = BigDecimal.ZERO;
    private BudgetSubgroup grupoSelecionado; // Grupo onde o produto será inserido

    @FXML
    public void initialize() {
        cbClientes.getItems().addAll(clientRepo.findAll());
        cbProduto.getItems().setAll(repo.findAll());

        // Listener para mudar o grupo atual ao clicar na lista
        listaGrupos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            grupoSelecionado = newVal;
        });
    }

    @FXML
    public void criarNovoGrupo() {
        String nome = txtNomeGrupo.getText();
        if (nome != null && !nome.trim().isEmpty()) {
            BudgetSubgroup novo = new BudgetSubgroup(nome);
            listaGrupos.getItems().add(novo);
            grupoSelecionado = novo; // Define como o grupo ativo
            txtNomeGrupo.clear();
        }
    }

    @FXML
    public void add() {
        if (grupoSelecionado == null) {
            mostrarAlerta("Selecione ou crie um subgrupo primeiro!");
            return;
        }

        Product p = cbProduto.getValue();
        if (p == null || txtQtd.getText().isEmpty()) return;

        BigDecimal qtd = new BigDecimal(txtQtd.getText().replace(",", "."));
        BigDecimal subtotalItem = p.getPrice().multiply(qtd);

        // Adiciona ao objeto do grupo
        grupoSelecionado.addItem(p.getName() + " x" + qtd + " = " + subtotalItem, subtotalItem);

        // Atualiza total geral
        totalGeral = totalGeral.add(subtotalItem);
        lblTotal.setText("Total Geral: R$ " + totalGeral);

        listaGrupos.refresh(); // Força a atualização visual da ListView
    }

    @FXML
    public void salvar() {
        Budget b = new Budget();

        b.setClient(cbClientes.getValue());
        b.setTotal(totalGeral);

        List<BudgetSubgroup> listaDeSubgrupos = new ArrayList<>(listaGrupos.getItems());


        b.setSubgroups(listaDeSubgrupos);

        budgetRepo.save(b);

        mostrarAlerta("Orçamento salvo com sucesso!");
        limparTela();
    }

    private void limparTela() {
        listaGrupos.getItems().clear();
        totalGeral = BigDecimal.ZERO;
        lblTotal.setText("Total: R$ 0.00");
        grupoSelecionado = null;
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(msg);
        a.show();
    }
}