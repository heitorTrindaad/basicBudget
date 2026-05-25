package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import atlantafx.base.theme.PrimerDark;

public class MainApp extends Application {

    private static BorderPane root;

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        root = FXMLLoader.load(getClass().getResource("/View/menu.fxml"));
        stage.setTitle("BasicBudget");
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    public static void setCenter(String fxml) throws Exception {
        root.setCenter(
                FXMLLoader.load(
                        MainApp.class.getResource("/View/" + fxml)));
    }

    public static void main(String[] args) {
        launch();
    }
}