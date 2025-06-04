package pt.ul.fc.di.css.javafxexample.controller.equipas;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pt.ul.fc.di.css.javafxexample.api.ApiEquipa;
import pt.ul.fc.di.css.javafxexample.api.ApiJogador;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.equipas.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.Posicao;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.UtilizadorDto;

public class DetalhesEquipaController extends Controller {

    private EquipaDto equipa;

    @FXML
    private Button adicionarJogadorButton;

    @FXML
    private ListView<JogadorDto> listaDeJogadores;

    @FXML
    private ListView<JogadorDto> jogadoresSelecionados;


    @FXML
    private TextField nomeInput;

    @FXML
    private Button removerJogadorButton;

    @FXML
    private Button voltarAtras;

    @FXML
    private TextArea messageArea;

    @FXML
    void adicionarJogador(ActionEvent event) {
        JogadorDto selecionado = listaDeJogadores.getSelectionModel().getSelectedItem();
        if (selecionado != null && !jogadoresSelecionados.getItems().contains(selecionado)) {
            jogadoresSelecionados.getItems().add(selecionado);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        Util.switchScene(getStage(), 
                        "/pt/ul/fc/di/css/javafxexample/view/equipas/init_equipa.fxml",
                        "Equipas");
    }

    @FXML
    void removerJogador(ActionEvent event) {
        JogadorDto selecionado = jogadoresSelecionados.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            jogadoresSelecionados.getItems().remove(selecionado);
        }
    }


    @FXML
    void removerEquipa(ActionEvent event) {
        try {
            ApiEquipa.removerEquipa(this.equipa.getId());
            goBack(event);
        } catch (Exception e) {
            this.getStage().setTitle(this.equipa.getId().toString());
            this.initialize();
            this.messageArea.setText("Algo correu mal ao tentar remover a Equipa");
        }
    }

    @FXML
    void atualizarEquipa(ActionEvent event) {
        List<Long> idsJogadores = this.jogadoresSelecionados.getItems().stream().map(j -> j.getUtilizador().getId()).toList();
        equipa.setJogadores(idsJogadores);
        equipa.setNome(this.nomeInput.getText());
        //FALTA PODER ALTERAR JOGOS
        try {
            ApiEquipa.atualizarEquipa(equipa);
        } catch (Exception e){
            this.messageArea.setText("Algo correu mal ao tentar atualizar a Equipa");
        }
    }

    @FXML
    public void initialize() {
        //get equipa with id (that is currently on the title)
        try {
            this.equipa = ApiEquipa.buscarEquipaPorId(Long.parseLong(this.getStage().getTitle()));
            this.getStage().setTitle(this.equipa.getNome());
        } catch (Exception error){
            this.getStage().setTitle("null");
            messageArea.setText("Não foi possível aceder aos dados da equipa pretendida, por favor volte a tentar");
        }

        //set nome
        this.nomeInput.setText(this.equipa.getNome());


        //set jogadores
        var jogadoresDisponiveis = FXCollections.observableArrayList(
            new JogadorDto(new UtilizadorDto(null, 100000001, "CristianoRonaldo dos Mocks", "Ronaldo"), Posicao.PIVO, new EstatisticaJogadorDto())
        );
        try {
            jogadoresDisponiveis = FXCollections.observableArrayList(ApiJogador.buscarJogadores());
            //jogadoresSelecionados = FXCollections.observableArrayList(ApiEquipa.buscarJogadoresDeEquipa(equipa.getId()));
        } catch (Exception e) {
            //Do nothing
            messageArea.setText("Houve um erro ao tentar buscar pelos jogadores");
        }
        listaDeJogadores.setItems(jogadoresDisponiveis);
    }
}
