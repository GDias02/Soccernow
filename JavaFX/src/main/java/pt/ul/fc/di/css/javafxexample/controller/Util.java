package pt.ul.fc.di.css.javafxexample.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Util {

    public static void switchScene(Stage stage, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Util.class.getResource(fxmlPath));
            Scene newScene = new Scene(loader.load());

            ControllerWithModel controller = loader.getController();
            controller.initModel(stage);

            stage.setTitle(title);
            stage.setScene(newScene);
            stage.show();

        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }
}
