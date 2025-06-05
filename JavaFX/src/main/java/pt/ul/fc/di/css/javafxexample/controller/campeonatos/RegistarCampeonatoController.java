package pt.ul.fc.di.css.javafxexample.controller.campeonatos;

import pt.ul.fc.di.css.javafxexample.api.ApiCampeonato;
import pt.ul.fc.di.css.javafxexample.api.ApiEquipa;
import pt.ul.fc.di.css.javafxexample.api.ApiJogo;
import pt.ul.fc.di.css.javafxexample.controller.Controller;

import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.di.css.javafxexample.dto.campeonatos.EstadoCampeonato;
import pt.ul.fc.di.css.javafxexample.dto.equipas.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.JogoDto;

import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class RegistarCampeonatoController extends Controller {

    private CampeonatoDto campeonatoDto;

    @FXML
    private Button adicionarEquipaBtn;

    @FXML
    private Button removerEquipaBtn;

    @FXML
    private Button confirmarbutton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField inputNome;

    @FXML
    private ListView<EquipaDto> listaEquipasDisponiveis;
    
    @FXML
    private ListView<EquipaDto> listaEquipasSelecionadas;

    @FXML
    private MenuButton tipoDeCampeonato;

    @FXML
    private TextArea messageArea;

    @FXML
    private MenuItem optionEliminatoria;

    @FXML
    private MenuItem optionPontos;

    @FXML
    void goBack(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/campeonatos/init_campeonato.fxml",
                "Campeonatos");        
    }

    
    @FXML
    void registarCampeonato(ActionEvent event) {
        if (this.inputNome.getText().equals("")) {
            messageArea.setText("É necessário que o campeonato tenha um nome");
            return;
        }
        
        if (this.datePicker.getValue() == null || this.datePicker.getValue().isBefore(LocalDate.now())){
            messageArea.setText("É necessário preencher uma data de início do campeonato e que seja no futuro");
            return;
        }

        if (listaEquipasSelecionadas.getItems().toArray().length < 2){
            messageArea.setText("É necessário que um campeonato tenha pelo menos duas equipas");
            return;
        }
        
        List<Long> idsJogos = List.of();
        List<Long> idsEquipas = new ArrayList<>();
        for (EquipaDto e : listaEquipasSelecionadas.getItems()){
            idsEquipas.add(e.getId());
        }
        Date date = Date.from(this.datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String tipoCampeonato = removerAcentos(this.tipoDeCampeonato.getText()).toLowerCase();
        this.campeonatoDto = //Make the campeonato
            new CampeonatoDto(0L, this.inputNome.getText(), EstadoCampeonato.AGENDADO,
                                 idsJogos, idsEquipas, date, tipoCampeonato);
        try {
            ApiCampeonato.registarCampeonato(campeonatoDto); 
            this.messageArea.setText("O campeonato " + this.campeonatoDto.getNome() + " foi registado com sucesso");                                
        } catch (Exception e) {
            this.messageArea.setText("Ocorreu um erro ao tentar criar o campeonato: " + e.getMessage());
        }
    }


    
    @FXML
    void adicionarEquipa(ActionEvent event) {
        var selecionada = listaEquipasDisponiveis.getSelectionModel().getSelectedItem();
        if (selecionada != null && !listaEquipasSelecionadas.getItems().contains(selecionada)) {
            listaEquipasSelecionadas.getItems().add(selecionada);
        }
    }

    @FXML
    void removerEquipa(ActionEvent event) {
        var selecionada = listaEquipasSelecionadas.getSelectionModel().getSelectedItem();
        if (selecionada != null ) {
            listaEquipasSelecionadas.getItems().remove(selecionada);
        }
    }

    @FXML
    void changeTipoEliminatoria(ActionEvent event) {
        this.tipoDeCampeonato.setText("Eliminatória");
    }

    @FXML
    void changeTipoPontos(ActionEvent event) {
        this.tipoDeCampeonato.setText("Pontos");
    }

    @FXML
    public void initialize(){
        javafx.application.Platform.runLater(() ->{ //para evitar o loading chato que quebra o flow
            var equipasInBD = FXCollections.observableArrayList(
                new EquipaDto()
            );
            try {
                equipasInBD = FXCollections.observableArrayList(ApiEquipa.buscarEquipas());
            } catch (Exception e) {
                messageArea.setText("Não foi possível obter as equipas");
            }
            listaEquipasDisponiveis.setItems(equipasInBD);
        });
    }

    private static String removerAcentos(String texto){
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return normalizado.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}