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
    private ListView<Budget> listHistory; // Alterado para o objeto Budget

    private BudgetRepositoryMemory repo = BudgetRepositoryMemory.getInstance();

    @FXML
    public void initialize() {
        atualizarLista();
    }

    public void atualizarLista() {
        listHistory.getItems().clear();
        listHistory.getItems().addAll(repo.findAll());
    }

    // --- FUNÇÃO: EXCLUIR ---
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
            repo.delete(selecionado.getId()); // Usa o método delete que você já tem
            atualizarLista();
        }
    }

    @FXML
    public void verDetalhes() {
        Budget selecionado = listHistory.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        // Monta a string com os itens salvos
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(selecionado.getClient().getName()).append("\n");
        sb.append("--------------------------\n");

        // Pega a lista de itens que salvamos no passo anterior
        for (String item : selecionado.getItensRelatorio()) {
            sb.append(item).append("\n");
        }

        sb.append("--------------------------\n");
        sb.append("Total: R$ ").append(selecionado.getTotal());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Itens do Orçamento #" + selecionado.getId());
        alert.setHeaderText("Produtos e Quantidades");
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }

    @FXML
    public void editarOrcamento() {
        Budget selecionado = listHistory.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAviso("Selecione um orçamento para editar.");
            return;
        }

        // Criando um diálogo customizado para editar Itens e Valor
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar Orçamento #" + selecionado.getId());
        dialog.setHeaderText("Altere os itens e o valor total");

        // Criando os campos
        TextArea txtItens = new TextArea(String.join("\n", selecionado.getItensRelatorio()));
        TextField txtTotal = new TextField(selecionado.getTotal().toString());

        VBox content = new VBox(10, new Label("Itens:"), txtItens, new Label("Valor Total (R$):"), txtTotal);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // 1. Atualiza os Itens
                selecionado.setItensRelatorio(java.util.Arrays.asList(txtItens.getText().split("\n")));

                // 2. Atualiza o Valor Total
                BigDecimal novoTotal = new BigDecimal(txtTotal.getText().replace(",", "."));
                selecionado.setTotal(novoTotal);

                // 3. Salva no Repositório
                repo.update(selecionado.getId(), selecionado);

                atualizarLista(); // Atualiza a ListView

            } catch (Exception e) {
                mostrarAviso("Erro ao salvar: Verifique se o valor total é um número válido.");
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