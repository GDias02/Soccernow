package pt.ul.fc.di.css.javafxexample.controller.campeonatos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;

public class InitCampeonatoController extends Controller{

    @FXML
    private Button buscarCampeonatoBtn;

    @FXML
    private Button cancelarCampeonatoBtn;

    @FXML
    private Button registarCampeonatoBtn;

    @FXML
    private Button voltarButton;

    @FXML
    void buscarCampeonatos(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/campeonatos/buscar_campeonato.fxml",
                "Buscar Campeonato");  
    }

    @FXML
    void cancelarCampeonato(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/campeonatos/cancelar_jogo_campeonato.fxml",
                "Cancelar Jogo de Um Campeonato");  
    }

    @FXML
    void goBack(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/init.fxml",
                "Soccernow");  
    }

    @FXML
    void registarCampeonato(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/campeonatos/registar_campeonato.fxml",
                "Registar Campeonato");  
    }

}