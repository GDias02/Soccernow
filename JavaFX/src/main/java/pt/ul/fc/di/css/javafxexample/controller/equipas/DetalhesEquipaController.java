package pt.ul.fc.di.css.javafxexample.controller.equipas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import pt.ul.fc.di.css.javafxexample.api.ApiJogo;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.equipas.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstadoDeJogo;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.JogoDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.LocalDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.MoradaDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.Posicao;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.UtilizadorDto;

public class DetalhesEquipaController extends Controller {

    private EquipaDto equipa;

    @FXML
    private ListView<JogoDto> listaDeJogos;

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
            this.messageArea.setText("A equipa " + this.equipa.getNome() + " foi eliminada");
        } catch (Exception e) {
            this.getStage().setTitle(this.equipa.getId().toString());
            this.messageArea.setText("Algo correu mal ao tentar remover a Equipa: " + e.getMessage());
        }
    }

    @FXML
    void atualizarEquipa(ActionEvent event) {
        List<Long> idsJogadores = this.jogadoresSelecionados.getItems().stream().map(j -> j.getUtilizador().getId()).toList();
        List<Long> idsJogos = this.listaDeJogos.getItems().stream().map(j -> j.getId()).toList();
        equipa.setJogadores(idsJogadores);
        equipa.setNome(this.nomeInput.getText());
        equipa.setHistoricoDeJogos(idsJogos);
        try {
            ApiEquipa.atualizarEquipa(equipa);
            this.messageArea.setText("A equipa foi alterada corretamente");
        } catch (Exception e){
            this.messageArea.setText("Algo correu mal ao tentar atualizar a Equipa" + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        //This run later needs to be here for .getStage() to work
        javafx.application.Platform.runLater(() -> {
            try {
                String title = this.getStage().getTitle();
                this.equipa = ApiEquipa.buscarEquipaPorId(Long.parseLong(title));
                if (this.equipa != null) {
                    //set nome
                    this.nomeInput.setText(this.equipa.getNome());

                    //set jogadores
                    var jogadoresDisponiveis = FXCollections.observableArrayList(
                        new JogadorDto(new UtilizadorDto(null, 100000001, "CristianoRonaldo dos Mocks", "Ronaldo"), Posicao.PIVO, new EstatisticaJogadorDto())
                    );
                    try {
                        jogadoresDisponiveis = FXCollections.observableArrayList(ApiJogador.buscarJogadores());
                        jogadoresSelecionados.setItems(
                            FXCollections.observableArrayList(ApiEquipa.buscarJogadoresDeEquipa(equipa.getId()))
                        );
                    } catch (Exception e) {
                        //Do nothing
                        messageArea.setText("Houve um erro ao tentar buscar pelos jogadores");
                    }
                    listaDeJogadores.setItems(jogadoresDisponiveis);

                    //set jogos
                    var jogosDaEquipa = FXCollections.observableArrayList(
                        new JogoDto()
                    );
                    
                    try {
                        jogosDaEquipa =  FXCollections.observableArrayList(ApiEquipa.buscarJogosDeEquipa(equipa.getId()));
                    } catch (Exception e) {
                        //Do nothing
                        System.out.println("Erro ao buscar jogos da equipa: " + e.getMessage());
                        messageArea.setText("Não foi possível obter os jogos da equipa");
                    }
                    listaDeJogos.setItems(jogosDaEquipa);
                    setBotoesDeRemocao();
                }
            } catch (Exception error) {
                messageArea.setText("Não foi possível aceder aos dados da equipa pretendida, por favor volte a tentar");
            }
            
        });
    }


    private void setBotoesDeRemocao(){
        listaDeJogos.setCellFactory(lv -> new javafx.scene.control.ListCell<JogoDto>() {
        private final Button apagarBtn = new Button("X");

        { //Classe anónima 
            apagarBtn.setOnAction(event -> {
                JogoDto jogo = getItem();
                if (jogo != null) {
                    getListView().getItems().remove(jogo);
                }
            });
        }

        @Override
        protected void updateItem(JogoDto jogo, boolean empty) {
            super.updateItem(jogo, empty);
            if (empty || jogo == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(jogo.toString()); // Ou personaliza como quiseres
                setGraphic(apagarBtn);
            }
        }
        });
    }
}
