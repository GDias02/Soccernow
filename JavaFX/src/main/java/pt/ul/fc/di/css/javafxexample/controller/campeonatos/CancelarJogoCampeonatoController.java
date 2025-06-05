package pt.ul.fc.di.css.javafxexample.controller.campeonatos;

import pt.ul.fc.di.css.javafxexample.api.ApiCampeonato;
import pt.ul.fc.di.css.javafxexample.api.ApiJogador;
import pt.ul.fc.di.css.javafxexample.api.ApiJogo;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstadoDeJogo;
import pt.ul.fc.di.css.javafxexample.dto.jogos.JogoDto;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class CancelarJogoCampeonatoController extends Controller {

    @FXML
    private Button confirmarRegisto;

    @FXML
    private ListView<CampeonatoDto> listaDeCampeonatos;

    @FXML
    private ListView<JogoDto> listaDeJogosDeUmCampeonato;

    @FXML
    private TextArea messageArea;

    @FXML
    private Button voltarAtras;

    @FXML
    void goBack(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/campeonatos/init_campeonato.fxml",
                "Campeonatos");        
    }

    @FXML
    void confirmarCancelamento(ActionEvent event) {
        var jogo = this.listaDeJogosDeUmCampeonato.getSelectionModel().getSelectedItem();
        if (jogo == null) 
            messageArea.setText("Por favor, selecione o jogo que quer cancelar");

        try {
            //a api cancela o próprio jogo no backend
            ApiJogo.cancelarJogo(jogo, jogo.getId());
            messageArea.setText("O jogo foi cancelado com sucesso");
        } catch (Exception e){
            messageArea.setText("Houve um erro ao cancelar o jogo: " + e.getMessage());
        }
        
    }

    @FXML
    public void initialize(){
        javafx.application.Platform.runLater(()->{
            var c = FXCollections.observableArrayList(
                new CampeonatoDto()
            );
            try {
                c = FXCollections.observableArrayList(ApiCampeonato.getCampeonatosAlteraveis());
            } catch (Exception e) {
                messageArea.setText("Houve um erro ao tentar buscar os campeonatos: " + e.getMessage());
            }
            listaDeCampeonatos.setItems(c);
            listaDeCampeonatos.setOnMouseClicked(event -> {
                CampeonatoDto selected = listaDeCampeonatos.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    mostraJogosDeCampeonato(selected);
                }
            });
        });
    }

    private void mostraJogosDeCampeonato(CampeonatoDto campeonatoDto){
        try {
            List<JogoDto> jogos = ApiCampeonato.getJogosDeCampeonato(campeonatoDto.getId());
            List<JogoDto> jogosAtivos = jogos.stream()
                .filter(j -> j.getEstadoDeJogo() != EstadoDeJogo.CANCELADO && j.getEstadoDeJogo() != EstadoDeJogo.TERMINADO)
                .toList(); //Mostra apenas os jogos canceláveis
            listaDeJogosDeUmCampeonato.setItems(FXCollections.observableArrayList(jogosAtivos));
        } catch (Exception e) {
            messageArea.setText("Não foi possível buscar os jogos deste campeonato");
        }
    }
}
