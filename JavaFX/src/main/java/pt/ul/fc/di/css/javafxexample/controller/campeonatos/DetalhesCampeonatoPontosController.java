package pt.ul.fc.di.css.javafxexample.controller.campeonatos;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import pt.ul.fc.di.css.javafxexample.api.ApiCampeonato;
import pt.ul.fc.di.css.javafxexample.api.ApiEquipa;
import pt.ul.fc.di.css.javafxexample.api.ApiJogo;
import pt.ul.fc.di.css.javafxexample.dto.campeonatos.CampeonatoPontosDto;
import pt.ul.fc.di.css.javafxexample.dto.campeonatos.EstadoCampeonato;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstadoDeJogo;

public class DetalhesCampeonatoPontosController extends DetalhesCampeonatoController {
        
    @FXML
    private ListView<String> tabelaDePontos;
    
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
        try {
            var tabela = this.campeonatoDto.getPontosDto().getTabela()
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(java.util.stream.Collectors.toList());
            
            for (var entry : tabela){
                var equipa = ApiEquipa.buscarEquipaPorId(entry.getKey());
                tabelaDePontos.getItems().add(equipa.toString() +" "+ entry.getValue().toString());
            }
            
        } catch (Exception e) {
            messageArea.setText("Não foi possível calcular as pontuações da tabela");
        }
    }

    protected void atualizarCampeonatoPontos(){
        try {
            List<Long> idsEquipas = this.equipasSelecionadas.getItems().stream().map(e-> e.getId()).toList();
            atualizarTabela(idsEquipas);
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

    private void atualizarTabela(List<Long> ids){
        Map<Long,Long> tabela = this.campeonatoDto.getPontosDto().getTabela();
        Set<Long> keysToRemove = tabela.keySet();
        for (var id:ids){
            if (!tabela.containsKey(id)){
                tabela.put(id, 0L);
                keysToRemove.remove(id);
            } 
        }
        for (var key: keysToRemove){
            tabela.remove(key);
        }
        var pontos = new CampeonatoPontosDto();
        pontos.setTabela(tabela);
        this.campeonatoDto.setPontosDto(pontos);
    }
}
