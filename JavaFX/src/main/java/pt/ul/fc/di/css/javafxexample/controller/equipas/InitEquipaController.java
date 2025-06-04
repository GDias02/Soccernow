package pt.ul.fc.di.css.javafxexample.controller.equipas;

import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstatisticaDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.Posicao;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.UtilizadorDto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class InitEquipaController extends Controller {

    @FXML
    private Button atualizarButton;

    @FXML
    private Button buscarButton;

    @FXML
    private Button criarButton;

    @FXML
    private Button removerButton;

    @FXML
    private Button backrButton;

    @FXML
    void goBack(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/init.fxml",
                "SoccerNow");
    }

    @FXML
    void openAtualizarEquipa(ActionEvent event) {

    }

    @FXML
    void openBuscarEquipas(ActionEvent event) {
        Util.switchScene(getStage(), 
            "/pt/ul/fc/di/css/javafxexample/view/equipas/buscar_equipa.fxml",
            "Buscar por Equipa");
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



}