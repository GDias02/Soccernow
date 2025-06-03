package pt.ul.fc.di.css.javafxexample.controller.jogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import pt.ul.fc.di.css.javafxexample.controller.Controller;

public class RegistarJogoController extends Controller {

  @FXML private Button addArbitroListaBtn;

  @FXML private Text arbitroPrincipal;

  @FXML private CheckBox campeonatoToggle;

  @FXML private TextField capacidadeLocal;

  @FXML private TextField cidadeMorada;

  @FXML private TextField codigoPostalMorada;

  @FXML private Button criarJogoSubmitBtn;

  @FXML private DatePicker diaDoJogo;

  @FXML private Button disponibilidadeArbitroBtn;

  @FXML private Text disponibilidadeArbitros;

  @FXML private Text disponibilidadeJogs1;

  @FXML private Button disponibilidadeJogs1Btn;

  @FXML private Text disponibilidadeJogs2;

  @FXML private Button disponibilidadeJogs2Btn;

  @FXML private Text disponibilidadeLocal;

  @FXML private Button disponibilidadeLocalBtn;

  @FXML private TextField estadoMorada;

  @FXML private ComboBox<?> horaDoJogo;

  @FXML private Text infoArbitroSelecionado;

  @FXML private Button loadJogadoresEquipa1Btn;

  @FXML private Button loadJogadoresEquipa2Btn;

  @FXML private TextField localidadeMorada;

  @FXML private ComboBox<?> minutosDoJogo;

  @FXML private TextField nomeLocal;

  @FXML private TextField paisMorada;

  @FXML private Button passo1AvancarBtn;

  @FXML private Button passo2AvancarBtn;

  @FXML private Button passo2VoltarBtn;

  @FXML private Button passo3AvancarBtn;

  @FXML private Button passo3VoltarBtn;

  @FXML private Button passo4AvancarBtn;

  @FXML private Button passo4VoltarBtn;

  @FXML private Button passo5VoltarBtn;

  @FXML private ComboBox<?> pickAlaD1;

  @FXML private ComboBox<?> pickAlaD2;

  @FXML private ComboBox<?> pickAlaE1;

  @FXML private ComboBox<?> pickAlaE2;

  @FXML private ComboBox<?> pickFixo1;

  @FXML private ComboBox<?> pickFixo2;

  @FXML private ComboBox<?> pickGuardaRedes1;

  @FXML private ComboBox<?> pickGuardaRedes2;

  @FXML private ComboBox<?> pickPivot1;

  @FXML private ComboBox<?> pickPivot2;

  @FXML private ComboBox<?> posicaoCapitao1;

  @FXML private ComboBox<?> posicaoCapitao2;

  @FXML private TextField proprietarioLocal;

  @FXML private Button removeArbitroListaBtn;

  @FXML private TextField ruaMorada;

  @FXML private GridPane selecao1Posicoes;

  @FXML private GridPane selecao2Posicoes;

  @FXML private ComboBox<?> selecionarArbitro;

  @FXML private ComboBox<?> selecionarCampeonatoBtn;

  @FXML private ComboBox<?> selecionarEquipa1Btn;

  @FXML private ComboBox<?> selecionarEquipa2Btn;

  @FXML private TableView<?> tabelaArbitros;

  @FXML private Button voltarAoMenuBtn;

  @FXML
  void addArbitroLista(ActionEvent event) {}

  @FXML
  void criarJogoSubmit(ActionEvent event) {}

  @FXML
  void enableCampeonato(ActionEvent event) {}

  @FXML
  void loadArbitroSelecionado(ActionEvent event) {}

  @FXML
  void loadJogadoresEquipa1(ActionEvent event) {}

  @FXML
  void loadJogadoresEquipa2(ActionEvent event) {}

  @FXML
  void passo1Avancar(ActionEvent event) {}

  @FXML
  void passo2Avancar(ActionEvent event) {}

  @FXML
  void passo2Voltar(ActionEvent event) {}

  @FXML
  void passo3Avancar(ActionEvent event) {}

  @FXML
  void passo3Voltar(ActionEvent event) {}

  @FXML
  void passo4Avancar(ActionEvent event) {}

  @FXML
  void passo4Voltar(ActionEvent event) {}

  @FXML
  void passo5Voltar(ActionEvent event) {}

  @FXML
  void removeArbitroLista(ActionEvent event) {}

  @FXML
  void verificarDisponibilidadeArbitro(ActionEvent event) {}

  @FXML
  void verificarDisponibilidadeJogs1(ActionEvent event) {}

  @FXML
  void verificarDisponibilidadeJogs2(ActionEvent event) {}

  @FXML
  void verificarDisponibilidadeLocal(ActionEvent event) {}

  @FXML
  void voltarAoMenu(ActionEvent event) {}
}
