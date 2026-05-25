package Controllers;

import Model.Budget;
import Service.budgetService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HistoryController {

        @FXML
        private TableView<Budget> tblHistorico;

        @FXML
        private TableColumn<Budget, String> colCliente;

        @FXML
        private TableColumn<Budget, String> colData;

        @FXML
        private TableColumn<Budget, String> colStatus;

        private final budgetService budService = budgetService.getInstance();

        @FXML
        public void initialize() {

                colCliente.setCellValueFactory(
                                cell -> new SimpleStringProperty(
                                                cell.getValue()
                                                                .getClient()
                                                                .getName()));

                colData.setCellValueFactory(
                                cell -> new SimpleStringProperty(
                                                cell.getValue()
                                                                .getDate()
                                                                .toString()));

                colStatus.setCellValueFactory(
                                cell -> new SimpleStringProperty(
                                                cell.getValue()
                                                                .isClosed()
                                                                                ? "FECHADO"
                                                                                : "PENDENTE"));

                carregarTabela();
        }

        private void carregarTabela() {

                tblHistorico.setItems(
                                FXCollections.observableArrayList(
                                                budService.findAll()));
        }

        @FXML
        public void editarOrcamento() {

                Budget budget = tblHistorico
                                .getSelectionModel()
                                .getSelectedItem();

                if (budget == null)
                        return;

                try {

                        FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                        "/View/budget.fxml"));

                        Parent root = loader.load();

                        BudgetController controller = loader.getController();

                        controller.carregarOrcamento(
                                        budget);

                        Stage stage = new Stage();

                        stage.setTitle(
                                        "Editar Orçamento");

                        stage.setScene(
                                        new Scene(
                                                        root,
                                                        1200,
                                                        800));

                        stage.setOnHidden(event -> {
                                carregarTabela();
                        });

                        stage.show();

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}