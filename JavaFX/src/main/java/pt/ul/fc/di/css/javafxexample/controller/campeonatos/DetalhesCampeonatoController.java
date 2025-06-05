package pt.ul.fc.di.css.javafxexample.controller.campeonatos;

import pt.ul.fc.di.css.javafxexample.api.ApiCampeonato;
import pt.ul.fc.di.css.javafxexample.api.ApiEquipa;
import pt.ul.fc.di.css.javafxexample.api.ApiJogo;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.di.css.javafxexample.dto.campeonatos.EstadoCampeonato;
import pt.ul.fc.di.css.javafxexample.dto.equipas.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstadoDeJogo;
import pt.ul.fc.di.css.javafxexample.dto.jogos.JogoDto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
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

    protected CampeonatoDto campeonatoDto;


    @FXML
    protected Button adicionarEquipa;

    @FXML
    protected Button adicionarJogo;
    
    @FXML
    protected Button removerEquipa;

    @FXML
    protected Button removerJogo;

    @FXML
    protected Button atualizarCampeonato;

    @FXML
    protected Button confirmarbutton1;

    @FXML
    protected DatePicker datePicker;

    @FXML
    protected ListView<EquipaDto> equipasDisponiveis;

    @FXML
    protected ListView<EquipaDto> equipasSelecionadas;

    @FXML
    protected SplitMenuButton estadoCampeontao;

    @FXML
    protected Button goBackButton;

    @FXML
    protected TextField inputNome;

    @FXML
    protected ListView<JogoDto> jogosDisponiveis;

    @FXML
    protected ListView<JogoDto> jogosSelecionados;

    @FXML
    protected TextArea messageArea;

    @FXML
    void goBack(ActionEvent event) {
        Util.switchScene(getStage(),
                "/pt/ul/fc/di/css/javafxexample/view/campeonatos/buscar_campeonato.fxml",
                "Buscar Campeonatos");        
    }

    @FXML
    void atualizarCampeonato(ActionEvent event) {
        if (!this.campeonatoDto.getEstado().equals(EstadoCampeonato.AGENDADO) 
            && !this.campeonatoDto.getEstado().equals(EstadoCampeonato.A_DECORRER)){
                this.messageArea.setText("Não se pode atualizar um campeonato cujo estado não seja 'agendado' ou 'a decorrer'.");
                return;
        }
        if (this.equipasSelecionadas.getItems().toArray().length == 0){
            this.messageArea.setText("Um campeonato necessita de ter equipas");
            return;
        }
        if (this.datePicker.getValue().isBefore(LocalDate.now())){
            this.messageArea.setText("A data precisa de ser depois de hoje");
            return;
        }
        if (campeonatoDto.getTipo().equals("pontos")){
            atualizarCampeonatoPontos();
            return;
        }
        try {
            List<Long> idsEquipas = this.equipasSelecionadas.getItems().stream().map(e-> e.getId()).toList();
            campeonatoDto.setEquipas(idsEquipas);
            List<Long> idsJogos = this.jogosSelecionados.getItems().stream().map(j->j.getId()).toList();
            var jogos = campeonatoDto.getJogos();
            jogos.addAll(idsJogos);
            campeonatoDto.setJogos(jogos);
            campeonatoDto.setEstado(EstadoCampeonato.valueOf(this.estadoCampeontao.getText().toUpperCase()));
            campeonatoDto.setNome(this.inputNome.getText());
            Date date = Date.from(this.datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            campeonatoDto.setDataInicio(date);
            ApiCampeonato.atualizarCampeonato(campeonatoDto);
            messageArea.setText("Campeonato Atualizado com sucesso");
        } catch (Exception e) {
            messageArea.setText("Ocorreu um erro ao tentar atualizar o campeonato: " + e.getMessage() );
        }
    }

    @FXML
    void removerCampeonato(ActionEvent event) {
        try{
            ApiCampeonato.removerCampeonato(this.campeonatoDto.getId());
            messageArea.setText("O campeonato foi removido com sucesso");
        } catch (Exception e){
            messageArea.setText("Não foi possível remover o campeontado :" + e.getMessage());
        }
    }

    @FXML
    void adicionarEquipa(ActionEvent event) {
        EquipaDto selectedEquipa = equipasDisponiveis.getSelectionModel().getSelectedItem();
        if (selectedEquipa != null && !equipasSelecionadas.getItems().contains(selectedEquipa)) {
            equipasSelecionadas.getItems().add(selectedEquipa);
        }
    }

    @FXML
    void adicionarJogo(ActionEvent event) {
        JogoDto selectedJogo = jogosDisponiveis.getSelectionModel().getSelectedItem();
        if (selectedJogo != null && !jogosSelecionados.getItems().contains(selectedJogo)) {
            jogosSelecionados.getItems().add(selectedJogo);
        }
    }

    @FXML
    void removerEquipa(ActionEvent event) {
        EquipaDto selectedEquipa = equipasSelecionadas.getSelectionModel().getSelectedItem();
        if (selectedEquipa != null) {
            equipasSelecionadas.getItems().remove(selectedEquipa);
        }
    }

    @FXML
    void removerJogo(ActionEvent event) {
        JogoDto selectedJogo = jogosSelecionados.getSelectionModel().getSelectedItem();
        if (selectedJogo != null) {
            jogosSelecionados.getItems().remove(selectedJogo);
        }
    }

    @FXML
    void disableEditButtons(){
        adicionarEquipa.setDisable(true);
        adicionarJogo.setDisable(true);
        removerEquipa.setDisable(true);
        removerJogo.setDisable(true);
        atualizarCampeonato.setDisable(true);
    }

    @FXML
    public void initialize(){
        //Add event listeners para o split menu button 
        //que determina o estado do campeonato
        for (MenuItem item : estadoCampeontao.getItems()){
            item.setOnAction(event -> {
                estadoCampeontao.setText(item.getText());
            });
        }
        javafx.application.Platform.runLater(() -> {
            try { //fetch campeonato
                String title = this.getStage().getTitle();
                this.campeonatoDto = ApiCampeonato.buscarCampeonato(Long.parseLong(title));
                this.getStage().setTitle(campeonatoDto.getNome());
                if (!this.campeonatoDto.getEstado().equals(EstadoCampeonato.AGENDADO)  && !this.campeonatoDto.getEstado().equals(EstadoCampeonato.A_DECORRER))    
                    disableEditButtons();
                if (campeonatoDto.getTipo().equals("pontos"))
                    initializePontos();
                if (campeonatoDto.getEstado().equals(EstadoCampeonato.A_DECORRER)){
                    adicionarEquipa.setDisable(true);
                    removerEquipa.setDisable(true);
                }
                
                try { //fetch it's equipas
                    var equipas = ApiEquipa.buscarEquipas();
                    this.equipasDisponiveis.setItems(FXCollections.observableArrayList(equipas));
                    var equipasS = equipas.stream()
                            .filter(e -> campeonatoDto.getEquipas().contains(e.getId()))
                            .toList();
                    this.equipasSelecionadas.setItems(FXCollections.observableArrayList(equipasS));
                    try { //fetch it's jogos
                        var jogos = ApiJogo.getAllJogos();
                        jogos = jogos.stream()
                                    .filter(j -> j.getCampeonato() != null)
                                    .toList();
                        this.jogosDisponiveis.setItems(FXCollections.observableArrayList(jogos));
                        var jogosS = jogos.stream()
                                        .filter(j->j.getCampeonato().equals(campeonatoDto.getId()) && j.getEstadoDeJogo().equals(EstadoDeJogo.AGENDADO))
                                        .toList();
                        this.jogosSelecionados.setItems(FXCollections.observableArrayList(jogosS));

                        //Set de todas as infos
                        this.inputNome.setText(campeonatoDto.getNome());
                        this.datePicker.setValue(
                            this.campeonatoDto.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        );
                        this.estadoCampeontao.setText(this.campeonatoDto.getEstado().toString().toLowerCase());
            
                    } catch (Exception e){
                        this.messageArea.setText("Ocorreu um problema ao tentar obter os jogos" + e.getMessage());
                    }
                } catch (Exception error) {
                    this.messageArea.setText("Ocorreu um erro ao tentar buscar as equipas" + error.getMessage());
                }
            } catch (Exception e) {
                this.messageArea.setText("Ocorreu um erro ao tentar receber as informações do seu campeonato" + e.getMessage());
            }
        });
    }

    protected void initializePontos(){
        //to be implemented by whoever exentds this one
    }

    protected void atualizarCampeonatoPontos(){
        //to be implemented by whoever extends this one
    }
} 