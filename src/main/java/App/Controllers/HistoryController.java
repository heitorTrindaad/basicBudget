package App.Controllers;

import Model.Budget;
import Repository.BudgetRepositoryMemory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.Optional;

public class HistoryController {

    @FXML
    private ListView<Budget> listHistory;

    private BudgetRepositoryMemory repo = BudgetRepositoryMemory.getInstance();

    @FXML
    public void initialize() {
        atualizarLista();
    }

    public void atualizarLista() {
        listHistory.getItems().clear();
        listHistory.getItems().addAll(repo.findAll());
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

        // Percorre os novos Subgrupos
        for (Model.BudgetSubgroup grupo : selecionado.getSubgroups()) {
            sb.append("\n=== ").append(grupo.getName().toUpperCase()).append(" ===\n");

            for (String item : grupo.getItems()) {
                sb.append("  • ").append(item).append("\n");
            }
            sb.append("Subtotal do Grupo: R$ ").append(grupo.getSubtotal()).append("\n");
        }

        sb.append("\n--------------------------\n");
        sb.append("TOTAL GERAL: R$ ").append(selecionado.getTotal());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes do Orçamento #" + selecionado.getId());
        alert.setHeaderText(null);

        // Usamos um ScrollPane para caso o texto seja muito longo
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
        for(Model.BudgetSubgroup g : selecionado.getSubgroups()) {
            for(String item : g.getItems()) {
                itensTexto.append(item).append("\n");
            }
        }

        TextArea txtItens = new TextArea(itensTexto.toString());
        TextField txtTotal = new TextField(selecionado.getTotal().toString());

        VBox content = new VBox(10, new Label("Itens (Simples):"), txtItens, new Label("Valor Total:"), txtTotal);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {

                Model.BudgetSubgroup grupoEditado = new Model.BudgetSubgroup("Editado");
                for (String linha : txtItens.getText().split("\n")) {
                    if (!linha.trim().isEmpty()) {
                        grupoEditado.addItem(linha, BigDecimal.ZERO); // Valor zero pois o total é manual aqui
                    }
                }

                selecionado.getSubgroups().clear();
                selecionado.getSubgroups().add(grupoEditado);
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