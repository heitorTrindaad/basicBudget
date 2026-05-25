package Controllers;

import Model.Budget;
import Model.BudgetSubgroup;
import Model.BudgetSubgetItem;
import Service.budgetService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HistoryController {

    @FXML
    private TableView<Budget> tblHistorico;
    @FXML
    private TableColumn<Budget, String> colCliente;
    @FXML
    private TableColumn<Budget, String> colData;
    @FXML
    private TableColumn<Budget, String> colTotal;
    @FXML
    private TableColumn<Budget, String> colStatus;

    @FXML
    private TextArea txtDetalhes;
    @FXML
    private Button btnAlterarStatus;

    private final budgetService budService = budgetService.getInstance();

    @FXML
    public void initialize() {
        // 1. Configura as colunas da tabela
        colCliente.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getClient() != null ? cell.getValue().getClient().getName() : "Sem Cliente"));
        colData.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getDate() != null ? cell.getValue().getDate().toString() : ""));
        colTotal.setCellValueFactory(
                cell -> new SimpleStringProperty(String.format("R$ %.2f", cell.getValue().getTotal())));
        colStatus.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().isClosed() ? "FECHADO" : "PENDENTE"));

        // Bloqueia ações enquanto nada está selecionado
        btnAlterarStatus.setDisable(true);

        carregarTabela();

        // 2. Escuta quando o usuário clica em um item da tabela
        tblHistorico.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mostrarDetalhes(newVal);
                btnAlterarStatus.setDisable(false);
                btnAlterarStatus.setText(newVal.isClosed() ? "Marcar como PENDENTE" : "Marcar como FECHADO");
                // Muda a cor do botão visualmente de acordo com a ação
                btnAlterarStatus.setStyle(newVal.isClosed() ? "-fx-background-color: #f44336; -fx-text-fill: white;"
                        : "-fx-background-color: #4CAF50; -fx-text-fill: white;");
            } else {
                txtDetalhes.clear();
                btnAlterarStatus.setDisable(true);
            }
        });
    }

    private void carregarTabela() {
        tblHistorico.setItems(FXCollections.observableArrayList(budService.findAll()));
        tblHistorico.refresh();
    }

    private void mostrarDetalhes(Budget b) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ORÇAMENTO ===\n");
        sb.append("Cliente: ").append(b.getClient() != null ? b.getClient().getName() : "N/A").append("\n");
        sb.append("Data: ").append(b.getDate()).append("\n");
        sb.append("STATUS: ").append(b.isClosed() ? "FECHADO (Faturado)" : "PENDENTE (Em negociação)").append("\n");
        sb.append("TOTAL GERAL: R$ ").append(b.getTotal()).append("\n\n");
        sb.append("--- ITENS DO ORÇAMENTO ---\n");

        if (b.getSubgroups() != null) {
            for (BudgetSubgroup grupo : b.getSubgroups()) {
                sb.append("[+] Grupo: ").append(grupo.getName()).append("\n");
                for (BudgetSubgetItem item : grupo.getItems()) {
                    sb.append("      -> ").append(item.getQuantity()).append("x ")
                            .append(item.getProduct().getName())
                            .append(" (R$ ").append(item.getProduct().getPrice()).append(" un) = R$ ")
                            .append(item.getSubtotal()).append("\n");
                }
                sb.append("    Subtotal do Grupo: R$ ").append(grupo.getSubtotal()).append("\n\n");
            }
        }
        txtDetalhes.setText(sb.toString());
    }

    @FXML
    public void alterarStatus() {
        Budget selecionado = tblHistorico.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            // Inverte o status de forma programática (segura)
            boolean novoStatus = !selecionado.isClosed();
            selecionado.setClosed(novoStatus);

            // Salva no banco/JSON
            budService.update(selecionado.getId(), selecionado);

            // Atualiza a interface
            carregarTabela();
            tblHistorico.getSelectionModel().select(selecionado); // Mantém selecionado

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("Status atualizado para: " + (novoStatus ? "FECHADO" : "PENDENTE"));
            a.show();
        }
    }
}