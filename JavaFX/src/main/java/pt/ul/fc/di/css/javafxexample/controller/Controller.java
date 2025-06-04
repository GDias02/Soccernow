package pt.ul.fc.di.css.javafxexample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public abstract class Controller implements ControllerWithModel {

    private Stage stage;

    @FXML
    private MenuItem menuUtilizadores;

    @FXML
    private MenuItem menuEquipas;

    @FXML
    private MenuItem menuJogos;

    @FXML
    private MenuItem menuCampeonatos;

    @FXML
    private MenuItem menuQuit;

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

    @FXML
    void initUtilizadores(ActionEvent event) {
        Util.switchScene(getStage(), 
                "/pt/ul/fc/di/css/javafxexample/view/utilizadores/init_utilizador.fxml",
                "Utilizadores");
    }

    @FXML
    void initEquipas(ActionEvent event) {
        Util.switchScene(getStage(), 
                "/pt/ul/fc/di/css/javafxexample/view/equipas/init_equipa.fxml",
                "Equipas");
    }

    @FXML
    void initJogos(ActionEvent event) {
        Util.switchScene(getStage(), 
                "/pt/ul/fc/di/css/javafxexample/view/jogos/init_jogo.fxml",
                "Jogos");
    }

    @FXML
    void initCampeonatos(ActionEvent event) {
        Util.switchScene(getStage(), 
                "/pt/ul/fc/di/css/javafxexample/view/campeonatos/init_campeonato.fxml",
                "Campeonatos");
    }

    @FXML
    void quit(ActionEvent event) {
        System.exit(0);
    }
    
}
