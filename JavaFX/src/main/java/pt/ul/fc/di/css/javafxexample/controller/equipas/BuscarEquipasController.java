package pt.ul.fc.di.css.javafxexample.controller.equipas;

import pt.ul.fc.di.css.javafxexample.api.ApiEquipa;
import pt.ul.fc.di.css.javafxexample.api.ApiJogador;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.equipas.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstatisticaJogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.Posicao;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.UtilizadorDto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class BuscarEquipasController extends Controller{


    @FXML
    private Button goBack;

    @FXML
    private TextField input;

    @FXML
    private ListView<EquipaDto> listaDeEquipas;

    @FXML
    private MenuButton tipoDePesquisa;

    @FXML
    void goBack(ActionEvent event) {
        Util.switchScene(getStage(), 
                        "/pt/ul/fc/di/css/javafxexample/view/equipas/init_equipa.fxml",
                        "Equipas");
    }

    @FXML
    public void initialize() {
        var equipasDisponiveis = FXCollections.observableArrayList(
            new EquipaDto(null, "Mock", null)
        );
        try {
            equipasDisponiveis = FXCollections.observableArrayList(ApiEquipa.buscarEquipas());
        } catch (Exception e) {
            //Do nothing
        }
        listaDeEquipas.setItems(equipasDisponiveis);
        listaDeEquipas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Util.switchScene(getStage(), 
                    "/pt/ul/fc/di/css/javafxexample/view/equipas/detalhes_equipa.fxml",
                    newValue.getId().toString());
            }
        });
    }

}