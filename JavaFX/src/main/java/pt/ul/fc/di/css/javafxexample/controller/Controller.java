package pt.ul.fc.di.css.javafxexample.controller;

import javafx.stage.Stage;

public abstract class Controller implements ControllerWithModel {

    private Stage stage;

    @Override
    public void initModel(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
