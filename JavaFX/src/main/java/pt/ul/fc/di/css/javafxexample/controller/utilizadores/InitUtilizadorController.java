package pt.ul.fc.di.css.javafxexample.controller.utilizadores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;

public class InitUtilizadorController extends Controller {

    @FXML
    private Button registarJogadorButton;

    @FXML
    private Button detalhesJogadorButton;

    @FXML
    private Button registarArbitroButton;

    @FXML
    private Button detalhesArbitroButton;

    @FXML
    private Button backButton;

    @FXML
    void registarJogador(ActionEvent event) {
        Util.switchScene(getStage(), 
                "/pt/ul/fc/di/css/javafxexample/view/utilizadores/registar_jogador.fxml",
                "Registar Jogador");
    }

    @FXML
    void detalhesJogador(ActionEvent event) {
        Util.switchScene(getStage(), 
                "/pt/ul/fc/di/css/javafxexample/view/utilizadores/detalhes_jogador.fxml",
                "Detalhes Jogador");
    }

    @FXML
    void registarArbitro(ActionEvent event) {
        Util.switchScene(getStage(), 
                "/pt/ul/fc/di/css/javafxexample/view/utilizadores/registar_arbitro.fxml",
                "Registar Arbitro");
    }

    @FXML
    void detalhesArbitro(ActionEvent event) {
        Util.switchScene(getStage(), 
                "/pt/ul/fc/di/css/javafxexample/view/utilizadores/detalhes_arbitro.fxml",
                "Detalhes Arbitro");
    }

    @FXML
    void back(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/init.fxml",
                "SoccerNow");
    }
}
