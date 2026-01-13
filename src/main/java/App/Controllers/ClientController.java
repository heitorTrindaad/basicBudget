package App.Controllers;

import Model.Client;
import Repository.ClientRepositoryMemory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;

    @FXML private TableView<Client> tableClientes;
    @FXML private TableColumn<Client, Integer> colId;
    @FXML private TableColumn<Client, String> colNome;
    @FXML private TableColumn<Client, String> colEmail;

    private final ClientRepositoryMemory repository =
            new ClientRepositoryMemory();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        atualizarLista();
    }

    @FXML
    public void salvar() {

        Client c = new Client();
        c.setName(txtNome.getText());
        c.setEmail(txtEmail.getText());

        repository.save(c);
        atualizarLista();
    }

    @FXML
    public void limpar() {
        txtNome.clear();
        txtEmail.clear();
    }

    private void atualizarLista() {
        tableClientes.getItems().setAll(repository.findAll());

    }
}
