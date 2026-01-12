package View;

import Model.*;
import Repository.*;
import Storage.AppData;
import Storage.DataStore;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class MainApp extends Application {

    ClientRepository clientRepo = new ClientRepositoryMemory();
    ProductRepository productRepo = new ProductRepositoryMemory();
    SaleRepository saleRepo = new SaleRepositoryMemory();
    BudgetRepository budgetRepo = new BudgetRepositoryMemory();

    ComboBox<Client> cbCliente = new ComboBox<>();
    ComboBox<Product> cbProduto = new ComboBox<>();

    Sale vendaAtual;
    AppData data;

    @Override
    public void start(Stage stage){

        data = DataStore.load(AppData.class);

        if(data == null) data = new AppData();

        clientRepo.setAll(data.clients);
        productRepo.setAll(data.products);
        saleRepo.setAll(data.sales);

        stage.setOnCloseRequest(e -> salvarTudo());

        TabPane tabs = new TabPane();

        Tab t1 = new Tab("Clientes", abaCliente());
        Tab t2 = new Tab("Produtos", abaProduto());
        Tab t3 = new Tab("Orçamento", abaOrcamento());

        t3.setOnSelectionChanged(e -> atualizarCombos());

        tabs.getTabs().addAll(t1,t2,t3);

        stage.setScene(new Scene(tabs,600,400));
        stage.show();
    }

    // ---------------- CLIENTE
    private VBox abaCliente(){

        TextField nome = new TextField();
        TextField email = new TextField();
        Button salvar = new Button("Salvar");

        salvar.setOnAction(e->{
            clientRepo.save(new Client(nome.getText(),email.getText()));
            nome.clear(); email.clear();
        });

        return new VBox(10,
                new Label("Nome"),nome,
                new Label("Email"),email,
                salvar);
    }

    // ---------------- PRODUTO
    private VBox abaProduto(){

        TextField nome = new TextField();
        TextField preco = new TextField();
        Button salvar = new Button("Salvar");

        salvar.setOnAction(e->{
            productRepo.save(
                    new Product(nome.getText(),
                            new BigDecimal(preco.getText()))
            );
            nome.clear(); preco.clear();
        });

        return new VBox(10,
                new Label("Produto"),nome,
                new Label("Preço"),preco,
                salvar);
    }

    // ---------------- ORÇAMENTO
    private VBox abaOrcamento(){

        TextField qtd = new TextField();
        Button novaVenda = new Button("Nova Venda");
        Button addItem = new Button("Adicionar Item");

        ListView<String> lista = new ListView<>();
        Label total = new Label("Total: 0");

        novaVenda.setOnAction(e->{
            vendaAtual = new Sale(cbCliente.getValue());
            saleRepo.save(vendaAtual);
            lista.getItems().clear();
            total.setText("Total: 0");
        });

        addItem.setOnAction(e->{

            Budget b = new Budget(
                    new BigDecimal(qtd.getText()),
                    "UN",
                    cbProduto.getValue(),
                    vendaAtual
            );

            vendaAtual.addItem(b);
            budgetRepo.save(b);

            lista.getItems().add(
                    b.getProduct().getName() +
                            " x " + b.getQuantity() +
                            " = " + b.getSubTotal()
            );

            total.setText("Total: " +
                    vendaAtual.getTotalAmount());
        });

        return new VBox(10,
                new Label("Cliente"),cbCliente,
                novaVenda,
                new Label("Produto"),cbProduto,
                new Label("Quantidade"),qtd,
                addItem,
                lista,
                total);
    }

    private void atualizarCombos(){
        cbCliente.getItems().setAll(clientRepo.findAll());
        cbProduto.getItems().setAll(productRepo.findAll());
    }

    public static void main(String[] args){
        launch();
    }

    private void salvarTudo(){
        data.clients = clientRepo.findAll();
        data.products = productRepo.findAll();
        data.sales = saleRepo.findAll();

        DataStore.save(data);
    }

}
