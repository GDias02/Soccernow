package pt.ul.fc.di.css.javafxexample.controller.equipas;

import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class InitEquipaController extends Controller {

    @FXML
    private Button atualizarButton;

    @FXML
    private Button buscarButton;

    @FXML
    private Button criarButton;

    @FXML
    private MenuItem menuCampeonatos;

    @FXML
    private MenuItem menuEquipas;

    @FXML
    private MenuItem menuJogos;

    @FXML
    private MenuItem menuQuit;

    @FXML
    private MenuItem menuUtilizadores;

    @FXML
    private Button removerButton;

    @FXML
    private Button voltarButton;

    @FXML
    void goBack(ActionEvent event) {

    }

    @FXML
    void openAtualizarEquipa(ActionEvent event) {

    }

    @FXML
    void openBuscarEquipas(ActionEvent event) {

    }

    @FXML
    void openCriarEquipa(ActionEvent event) {
        Util.switchScene(getStage(), 
            "/pt/ul/fc/di/css/javafxexample/view/equipas/registar_equipa.fxml",
            "Criar");
    }

    @FXML
    void openRemoverEquipa(ActionEvent event) {

    }

    @FXML
    void initCampeonatos(ActionEvent event) {

    }

    @FXML
    void initEquipas(ActionEvent event) {

    }

    @FXML
    void initJogos(ActionEvent event) {

    }

    @FXML
    void initUtilizadores(ActionEvent event) {

    }

    @FXML
    void quit(ActionEvent event) {

    }

}