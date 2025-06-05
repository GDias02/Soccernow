package pt.ul.fc.di.css.javafxexample.controller.campeonatos;

import pt.ul.fc.di.css.javafxexample.api.ApiCampeonato;
import pt.ul.fc.di.css.javafxexample.api.ApiJogador;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.di.css.javafxexample.dto.campeonatos.EstadoCampeonato;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.Posicao;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.UtilizadorDto;

import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class BuscarCampeonatoController extends Controller{


    @FXML
    private Button goBack;

    @FXML
    private TextField input;

    @FXML
    private ListView<CampeonatoDto> listaDeCampeonatos;

    @FXML
    private MenuButton tipoDePesquisa;

    @FXML
    void goBack(ActionEvent event) {
        Util.switchScene(getStage(), 
                        "/pt/ul/fc/di/css/javafxexample/view/campeonatos/init_campeonato.fxml",
                        "Campeonatos");
    }

    @FXML
    public void initialize() {
        var campeonatosDisponiveis = FXCollections.observableArrayList(
            new CampeonatoDto()
        );
        try {
            campeonatosDisponiveis = FXCollections.observableArrayList(ApiCampeonato.buscarCampeonatos());
        } catch (Exception e) {
            //Do nothing
        }
        listaDeCampeonatos.setItems(campeonatosDisponiveis);
        listaDeCampeonatos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getTipo().equals("pontos")){ //Campeonatos de pontos têm views diferentes
                    Util.switchScene(getStage(), 
                    "/pt/ul/fc/di/css/javafxexample/view/campeonatos/detalhes_campeonato_pontos.fxml",
                    newValue.getId().toString());
                } else {
                    Util.switchScene(getStage(), 
                        "/pt/ul/fc/di/css/javafxexample/view/campeonatos/detalhes_campeonato.fxml",
                        newValue.getId().toString());
                }
            }
        });
    }

}