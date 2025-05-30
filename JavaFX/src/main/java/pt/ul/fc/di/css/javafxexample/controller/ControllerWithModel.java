package pt.ul.fc.di.css.javafxexample.controller;

import javafx.stage.Stage;

public interface ControllerWithModel {
    void initModel(Stage stage);

    Stage getStage();

    void setStage(Stage stage);
}
