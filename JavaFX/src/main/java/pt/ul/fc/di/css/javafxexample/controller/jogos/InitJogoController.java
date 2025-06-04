package pt.ul.fc.di.css.javafxexample.controller.jogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;

public class InitJogoController extends Controller {

  @FXML private MenuButton esquecimeBtn;

  @FXML private Button registarJogoBtn;

  @FXML private Button registarLocalBtn;

  @FXML private Button registarResultadoBtn;

  @FXML
  void registarJogo(ActionEvent event) {
    Util.switchScene(
        getStage(), "/pt/ul/fc/di/css/javafxexample/view/jogos/registar_jogo.fxml", "Criar Jogo");
  }

  @FXML
  void registarLocal(ActionEvent event) {}

  @FXML
  void registarResultado(ActionEvent event) {
    Util.switchScene(
        getStage(),
        "/pt/ul/fc/di/css/javafxexample/view/jogos/registar_resultado_jogo.fxml",
        "Resultado de Jogo");
  }
}
