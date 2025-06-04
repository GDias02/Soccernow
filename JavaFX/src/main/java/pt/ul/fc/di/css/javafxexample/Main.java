package pt.ul.fc.di.css.javafxexample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pt.ul.fc.di.css.javafxexample.controller.LoginController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String prefix = "/pt/ul/fc/di/css/javafxexample/view/";

        BorderPane root = new BorderPane();
        FXMLLoader initLoader = new FXMLLoader(getClass().getResource(prefix + "login.fxml"));
        root.setCenter(initLoader.load());
        LoginController loginController = initLoader.getController();

        loginController.initModel(primaryStage);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SoccerNow");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
