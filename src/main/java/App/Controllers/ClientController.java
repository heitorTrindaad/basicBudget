package App.Controllers;

import Model.Client;
import Repository.ClientRepositoryMemory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtCnpj;
    @FXML private TextField txtTelefone;

    @FXML private TableView<Client> tableClientes;
    @FXML private TableColumn<Client, Integer> colId;
    @FXML private TableColumn<Client, String> colNome;
    @FXML private TableColumn<Client, String> colEmail;
    @FXML private TableColumn<Client, String> colCnpj;
    @FXML private TableColumn<Client, String> colTelefone;

    private final ClientRepositoryMemory repository =
            new ClientRepositoryMemory();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        atualizarLista();
    }

    @FXML
    public void salvar() {

        Client c = new Client();
        c.setName(txtNome.getText());
        c.setEmail(txtEmail.getText());
        c.setCnpj(txtCnpj.getText());
        c.setTelefone(txtTelefone.getText());

        repository.save(c);
        atualizarLista();
    }

    @FXML
    public void limpar() {
        txtNome.clear();
        txtEmail.clear();
        txtCnpj.clear();
        txtTelefone.clear();
    }

    private void atualizarLista() {
        tableClientes.getItems().setAll(repository.findAll());


    }
}
