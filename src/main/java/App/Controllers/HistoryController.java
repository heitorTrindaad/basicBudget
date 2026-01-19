package App.Controllers;

import Repository.BudgetRepositoryMemory;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class HistoryController {

    @FXML
    private ListView<String> listHistory;

    private BudgetRepositoryMemory repo =
            BudgetRepositoryMemory.getInstance();

    @FXML
    public void initialize() {
        atualizarLista();
    }

    public void atualizarLista() {

        listHistory.getItems().clear();

        repo.findAll().forEach(b -> {
            listHistory.getItems().add(b.toString());
        });
    }
}
