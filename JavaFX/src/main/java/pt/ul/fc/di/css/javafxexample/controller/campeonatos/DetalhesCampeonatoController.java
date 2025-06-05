package pt.ul.fc.di.css.javafxexample.controller.campeonatos;

import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.equipas.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.JogoDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DetalhesCampeonatoController extends Controller {

    @FXML
    private Button atualizarCampeonato;

    @FXML
    private Button confirmarbutton1;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<EquipaDto> equipasDisponiveis;

    @FXML
    private ListView<EquipaDto> equipasSelecionadas;

    @FXML
    private SplitMenuButton estadoCampeontao;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField inputNome;

    @FXML
    private ListView<JogoDto> jogosDisponiveis;

    @FXML
    private ListView<JogoDto> jogosSelecionados;

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
    private TextArea messageArea;

    @FXML
    void goBack(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/campeonatos/buscar_campeonato.fxml",
                "Buscar Campeonatos");        
    }

    @FXML
    void atualizarCampeonato(ActionEvent event) {

    }

    @FXML
    void removerCampeonato(ActionEvent event) {

    }

} 