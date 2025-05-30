package pt.ul.fc.di.css.javafxexample.controller.utilizadores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;

public class InitUtilizadorController extends Controller {

    @FXML
    private Button backButton;

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
    void back(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/init.fxml",
                "SoccerNow");
    }

    @FXML
    void quit(ActionEvent event) {
        System.exit(0);
    }
}
