package App.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private void openClientes() {
        abrirTela("client.fxml", "Clientes");
    }

    @FXML
    private void openProdutos() {
        abrirTela("product.fxml", "Produtos");
    }

    @FXML
    private void openHistorico() {
        abrirTela("history.fxml", "Histórico");
    }

    @FXML
    private void openOrcamento() {
        abrirTela("budget.fxml", "Orçamento");
    }

    private void abrirTela(String fxml, String titulo) {
        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/" + fxml)
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
