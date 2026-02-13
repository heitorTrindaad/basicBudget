package App.Controllers;

import Model.Budget;
import Model.BudgetSubgroup;
import Model.BudgetSubgetItem;
import Repository.BudgetRepositoryMemory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.Optional;

public class HistoryController {

    @FXML
    private ListView<Budget> listHistory;

    @FXML
    private TextField txtPesquisa;

    private BudgetRepositoryMemory repo = BudgetRepositoryMemory.getInstance();
    private ObservableList<Budget> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        atualizarLista();

        // 1. Criamos a FilteredList envolvendo os dados originais
        FilteredList<Budget> filteredData = new FilteredList<>(masterData, p -> true);

        // 2. Ouvinte de texto (dispara sempre que o usuário digita algo)
        txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(budget -> {
                // Se o campo estiver vazio, mostra tudo
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Regras de busca: Nome do cliente ou ID do orçamento
                if (budget.getClient().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(budget.getId()).contains(lowerCaseFilter)) {
                    return true;
                }

                return false; // Não encontrou nada
            });
        });

        // 3. Define a lista filtrada como a fonte da ListView
        listHistory.setItems(filteredData);
    }

    public void atualizarLista() {
        // 1. Buscamos os dados atualizados do repositório
        var dadosDoRepo = repo.findAll();

        // 2. Atualizamos apenas a masterData.
        // A FilteredList e a ListView perceberão a mudança sozinhas.
        masterData.setAll(dadosDoRepo);
    }

    @FXML
    public void excluirOrcamento() {
        Budget selecionado = listHistory.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAviso("Selecione um orçamento para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir o orçamento #" + selecionado.getId() + "?");

        Optional<ButtonType> result = confirmacao.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            repo.delete(selecionado.getId());
            atualizarLista();
        }
    }

    @FXML
    public void verDetalhes() {
        Budget selecionado = listHistory.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(selecionado.getClient().getName()).append("\n");

        for (BudgetSubgroup grupo : selecionado.getSubgroups()) {
            sb.append("\n=== ").append(grupo.getName().toUpperCase()).append(" ===\n");

            for (BudgetSubgetItem item : grupo.getItems()) {
                sb.append("  • ").append(item.toString()).append("\n");
            }
            sb.append("Subtotal do Grupo: R$ ").append(grupo.getSubtotal()).append("\n");
        }

        sb.append("\n--------------------------\n");
        sb.append("TOTAL GERAL: R$ ").append(selecionado.getTotal());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes do Orçamento #" + selecionado.getId());
        alert.setHeaderText(null);

        TextArea area = new TextArea(sb.toString());
        area.setEditable(false);
        area.setWrapText(true);
        alert.getDialogPane().setContent(area);

        alert.showAndWait();
    }

    @FXML
    public void editarOrcamento() {
        Budget selecionado = listHistory.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar Orçamento #" + selecionado.getId());

        StringBuilder itensTexto = new StringBuilder();
        for(BudgetSubgroup g : selecionado.getSubgroups()) {
            for(BudgetSubgetItem item : g.getItems()) {
                itensTexto.append(item.toString()).append("\n");
            }
        }

        TextArea txtItens = new TextArea(itensTexto.toString());
        txtItens.setPromptText("Nota: Itens editados aqui serão convertidos em descrições simples.");
        TextField txtTotal = new TextField(selecionado.getTotal().toString());

        VBox content = new VBox(10, new Label("Visualização dos Itens:"), txtItens, new Label("Valor Total:"), txtTotal);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                BudgetSubgroup grupoEditado = new BudgetSubgroup("Editado via Histórico");

                for (String linha : txtItens.getText().split("\n")) {
                    if (!linha.trim().isEmpty()) {
                        BudgetSubgetItem novo = new BudgetSubgetItem(null, BigDecimal.ONE, BigDecimal.ZERO) {
                            @Override
                            public String toString() { return linha; }
                        };
                        grupoEditado.addItem(novo);
                    }
                }

                selecionado.setTotal(new BigDecimal(txtTotal.getText().replace(",", ".")));
                repo.update(selecionado.getId(), selecionado);
                atualizarLista();
            } catch (Exception e) {
                mostrarAviso("Erro na conversão dos valores.");
            }
        }
    }

    private void mostrarAviso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}