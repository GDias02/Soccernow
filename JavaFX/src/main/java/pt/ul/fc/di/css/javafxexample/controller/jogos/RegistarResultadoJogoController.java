package pt.ul.fc.di.css.javafxexample.controller.jogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;

public class RegistarResultadoJogoController extends Controller {

  @FXML private Button addCartaoListaBtn;

  @FXML private Button addCartaoListaBtn1;

  @FXML private Text equipaVencedoraDisplay;

  @FXML private Button passo1AvancarBtn1;

  @FXML private Button passo2AvancarBtn;

  @FXML private Button passo2AvancarBtn1;

  @FXML private Button passo2VoltarBtn;

  @FXML private Button passo2VoltarBtn1;

  @FXML private Button passo4VoltarBtn;

  @FXML private Text placarDIsplay;

  @FXML private Button removeCartaoListaBtn;

  @FXML private Button removeCartaoListaBtn1;

  @FXML private Button resultadoJogoSubmitBtn;

  @FXML private ComboBox<?> selecionarArbitro;

  @FXML private ComboBox<?> selecionarCor;

  @FXML private ComboBox<?> selecionarHora;

  @FXML private ComboBox<?> selecionarHora1;

  @FXML private ComboBox<?> selecionarJogador;

  @FXML private ComboBox<?> selecionarJogador1;

  @FXML private ComboBox<?> selecionarMinuto;

  @FXML private ComboBox<?> selecionarMinuto1;

  @FXML private TableView<?> tabelaArbitros;

  @FXML private TableView<?> tabelaArbitros1;

  @FXML private TableView<?> tabelaJogos;

  @FXML private Button voltarAoMenuBtn;

  @FXML
  void addCartaoLista(ActionEvent event) {}

  @FXML
  void loadArbitroSelecionado(ActionEvent event) {}

  @FXML
  void passo1Avancar(ActionEvent event) {}

  @FXML
  void passo2Avancar(ActionEvent event) {}

  @FXML
  void passo2Voltar(ActionEvent event) {}

  @FXML
  void passo4Voltar(ActionEvent event) {}

  @FXML
  void removeCartaoLista(ActionEvent event) {}

  @FXML
  void resultadoJogoSubmit(ActionEvent event) {}

  @FXML
  void voltarAoMenu(ActionEvent event) {
    Util.switchScene(
        getStage(), "/pt/ul/fc/di/css/javafxexample/view/jogos/init_jogo.fxml", "Jogos");
  }
}
