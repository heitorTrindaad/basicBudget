package Controllers;

import Model.Client;
import Service.clientService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientController {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCnpj;
    @FXML
    private TextField txtTelefone;

    @FXML
    private TableView<Client> tableClientes;
    @FXML
    private TableColumn<Client, Integer> colId;
    @FXML
    private TableColumn<Client, String> colNome;
    @FXML
    private TableColumn<Client, String> colEmail;
    @FXML
    private TableColumn<Client, String> colCnpj;
    @FXML
    private TableColumn<Client, String> colTelefone;

    private Client clienteSelecionado;

    private final clientService service = clientService.getInstance();

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
    public void carregarCliente() {
        clienteSelecionado = tableClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            txtNome.setText(clienteSelecionado.getName());
            txtEmail.setText(clienteSelecionado.getEmail());
            txtCnpj.setText(clienteSelecionado.getCnpj());
            txtTelefone.setText(clienteSelecionado.getTelefone());
        }
    }

    @FXML
    public void salvar() {
        if (clienteSelecionado == null) {
            clienteSelecionado = new Client();
        }

        clienteSelecionado.setName(txtNome.getText());
        clienteSelecionado.setEmail(txtEmail.getText());
        clienteSelecionado.setCnpj(txtCnpj.getText());
        clienteSelecionado.setTelefone(txtTelefone.getText());

        service.save(clienteSelecionado);
        atualizarLista();
        limpar();
    }

    @FXML
    public void limpar() {
        txtNome.clear();
        txtEmail.clear();
        txtCnpj.clear();
        txtTelefone.clear();
        clienteSelecionado = null;
    }

    private void atualizarLista() {
        tableClientes.getItems().setAll(service.findAll());
    }
}