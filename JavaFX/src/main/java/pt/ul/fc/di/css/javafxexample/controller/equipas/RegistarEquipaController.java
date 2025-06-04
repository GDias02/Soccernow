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
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.dto.equipas.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.Posicao;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.UtilizadorDto;

public class RegistarEquipaController extends Controller {

    @FXML
    private Button adicionarJogadorButton;

    @FXML
    private Button confirmarRegisto;

    @FXML
    private ListView<JogadorDto> listaDeJogadores;

    @FXML
    private ListView<JogadorDto> jogadoresSelecionados;

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
    void confirmarRegisto(ActionEvent event) {
        String nomeEquipa = nomeInput.getText();
        List<JogadorDto> jogadores = new ArrayList<>(jogadoresSelecionados.getItems());

        if (nomeEquipa.isBlank()) {
            //System.out.println("Por favor, insira o nome e selecione pelo menos um jogador.");
            messageArea.setText("A equipa necessita de pelo menos ter um nome");
            return;
        }

        // TODO: Chamar o serviço de backend para criar equipa com nomeEquipa e jogadores
        System.out.println("Equipa '" + nomeEquipa + "' registada com " + jogadores.size() + " jogador(es).");
        List<Long> idJogadores = jogadores.stream()
            .filter(j -> j.getUtilizador() != null)
            .map(j -> j.getUtilizador().getId())
            .toList();
        EquipaDto equipa = new EquipaDto(0L, nomeEquipa, idJogadores);
        try {
            ApiEquipa.createEquipa(equipa);
        } catch (Exception e) {
            messageArea.setText("Erro ao criar equipa: " + e.getMessage());
        } 
    }

    @FXML
    void goBack(ActionEvent event) {

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

    @FXML
    void removerJogador(ActionEvent event) {
        JogadorDto selecionado = jogadoresSelecionados.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            jogadoresSelecionados.getItems().remove(selecionado);
        }
    }
    @FXML
    public void initialize() {
        var jogadoresDisponiveis = FXCollections.observableArrayList(
            new JogadorDto(new UtilizadorDto(null, 123456789, "Cristiano", "Ronaldo"), Posicao.PIVO, new EstatisticaJogadorDto()),
            new JogadorDto(new UtilizadorDto(null, 987654321, "João", "Mário"), Posicao.ALA_ESQUERDA, new EstatisticaJogadorDto()),
            new JogadorDto(new UtilizadorDto(null, 192837465, "Pepe", "Ferreira"), Posicao.FIXO, new EstatisticaJogadorDto())
        );

        listaDeJogadores.setItems(jogadoresDisponiveis);
        jogadoresSelecionados.setItems(FXCollections.observableArrayList());
    }
}
