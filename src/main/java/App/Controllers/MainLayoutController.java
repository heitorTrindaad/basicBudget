package App.Controllers;

import Service.budgetService;
import Model.Budget;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class MainLayoutController {

    @FXML
    private VBox sidebar;
    @FXML
    private Button btnToggleSidebar;
    @FXML
    private Label lblNomeEmpresa;
    @FXML
    private Button btnDashboard, btnClientes, btnProdutos, btnOrcamentos, btnHistorico;

    @FXML
    private ToggleButton btnFiltroMes;
    @FXML
    private javafx.scene.chart.BarChart<String, Number> chartOrcamentos;

    @FXML
    private javafx.scene.layout.StackPane contentArea;
    @FXML
    private VBox dashboardView;

    private boolean isSidebarMinimized = false;
    private final budgetService bService = budgetService.getInstance();

    @FXML
    public void initialize() {
        carregarDadosGrafico(true);
    }

    @FXML
    private void handleToggleSidebar() {
        if (!isSidebarMinimized) {
            sidebar.setPrefWidth(60.0);
            lblNomeEmpresa.setVisible(false);
            btnDashboard.setText("📊");
            btnClientes.setText("👥");
            btnProdutos.setText("📦");
            btnOrcamentos.setText("💼");
            btnHistorico.setText("📜");
            btnToggleSidebar.setText("▶");
            isSidebarMinimized = true;
        } else {
            sidebar.setPrefWidth(220.0);
            lblNomeEmpresa.setVisible(true);
            btnDashboard.setText("📊 Dashboard");
            btnClientes.setText("👥 Clientes");
            btnProdutos.setText("📦 Produtos");
            btnOrcamentos.setText("💼 Orçamentos");
            btnHistorico.setText("📜 Histórico");
            btnToggleSidebar.setText("◀");
            isSidebarMinimized = false;
        }
    }

    @FXML
    private void handleFiltroMudou() {
        boolean porMes = btnFiltroMes.isSelected();
        carregarDadosGrafico(porMes);
    }

    private void carregarDadosGrafico(boolean porMes) {
        chartOrcamentos.getData().clear();
        List<Budget> orcamentos = bService.findAll();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        if (porMes) {
            chartOrcamentos.setTitle("Faturamento por Mês (Ano Atual)");

            Map<String, Double> dadosAgrupados = orcamentos.stream()
                    .filter(b -> b.getDate() != null)
                    .collect(Collectors.groupingBy(
                            b -> b.getDate().getMonth().getDisplayName(TextStyle.SHORT, new Locale("pt", "BR")),
                            Collectors.summingDouble(b -> b.getTotal().doubleValue())));

            dadosAgrupados.forEach((mes, total) -> series.getData().add(new XYChart.Data<>(mes, total)));
        } else {
            chartOrcamentos.setTitle("Faturamento Anual Histórico");

            Map<String, Double> dadosAgrupados = orcamentos.stream()
                    .filter(b -> b.getDate() != null)
                    .collect(Collectors.groupingBy(
                            b -> String.valueOf(b.getDate().getYear()),
                            Collectors.summingDouble(b -> b.getTotal().doubleValue())));

            dadosAgrupados.forEach((ano, total) -> series.getData().add(new XYChart.Data<>(ano, total)));
        }

        // ... fim do método carregarDadosGrafico ...
        chartOrcamentos.getData().add(series);

        // === ADICIONE ESTE BLOCO LOGO ABAIXO DO ADICIONAR SÉRIE ===
        int quantidadeDeBarras = series.getData().size();

        if (quantidadeDeBarras == 1) {
            chartOrcamentos.setCategoryGap(450); // Muita folga se for só 1 mês
        } else if (quantidadeDeBarras == 2) {
            chartOrcamentos.setCategoryGap(250); // Folga média para 2 meses
        } else if (quantidadeDeBarras == 3) {
            chartOrcamentos.setCategoryGap(150); // Folga menor para 3 meses
        } else {
            chartOrcamentos.setCategoryGap(20); // Espaçamento padrão profissional para 4 meses ou mais
        }
    }

    // Método utilitário para carregar qualquer tela dentro do painel central
    private void trocarTelaCentral(String caminhoFxml) {
        try {
            // Carrega o arquivo FXML da tela desejada
            javafx.scene.Parent novaTela = javafx.fxml.FXMLLoader.load(getClass().getResource(caminhoFxml));

            // Limpa o que estava no centro (o gráfico ou a tela anterior)
            contentArea.getChildren().clear();

            // Injeta a nova tela no espaço central
            contentArea.getChildren().add(novaTela);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela: " + caminhoFxml);
        }
    }

    // Agora os seus métodos ganham vida e chamam as suas telas reais:
    @FXML
    private void showDashboard() {
        // Para voltar ao Dashboard, basta recarregar o próprio painel do gráfico
        contentArea.getChildren().clear();
        contentArea.getChildren().add(dashboardView);
        carregarDadosGrafico(btnFiltroMes.isSelected());
    }

    @FXML
    private void showClientes() {
        // Carrega a sua tela de clientes existente dentro do centro
        trocarTelaCentral("/View/client.fxml");
    }

    @FXML
    private void showProdutos() {
        // Carrega a sua tela de produtos existente dentro do centro
        trocarTelaCentral("/View/product.fxml");
    }

    @FXML
    private void showOrcamentos() {
        // Quando você criar a tela de gerenciamento de orçamentos, mude aqui:
        trocarTelaCentral("/View/budget.fxml");
    }

    @FXML
    private void showHistorico() {
        trocarTelaCentral("/View/history.fxml");
    }
}