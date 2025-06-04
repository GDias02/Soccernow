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

        List<Long> idJogadores = jogadores.stream()
            .filter(j -> j.getUtilizador() != null)
            .map(j -> j.getUtilizador().getId())
            .toList();
        EquipaDto equipa = new EquipaDto(0L, nomeEquipa, idJogadores);

        try {
            ApiEquipa.createEquipa(equipa);
            messageArea.setText(nomeEquipa + " foi criada com sucesso");
            goBack(event);
        } catch (Exception e) {
            messageArea.setText("Erro ao criar equipa: " + e.getMessage());
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
    public void initialize() {
        var jogadoresDisponiveis = FXCollections.observableArrayList(
            new JogadorDto(new UtilizadorDto(null, 123456789, "MockCristiano", "Ronaldo"), Posicao.PIVO, new EstatisticaJogadorDto()),
            new JogadorDto(new UtilizadorDto(null, 987654321, "MockJoão", "Mário"), Posicao.ALA_ESQUERDA, new EstatisticaJogadorDto()),
            new JogadorDto(new UtilizadorDto(null, 192837465, "MockPepe", "Ferreira"), Posicao.FIXO, new EstatisticaJogadorDto())
        );
        try {
            jogadoresDisponiveis = FXCollections.observableArrayList(ApiJogador.buscarJogadores());
        } catch (Exception e) {
            //Do nothing
            messageArea.setText("Houve um erro ao tentar buscar pelos jogadores");
        }
        listaDeJogadores.setItems(jogadoresDisponiveis);
        jogadoresSelecionados.setItems(FXCollections.observableArrayList());
    }
}
