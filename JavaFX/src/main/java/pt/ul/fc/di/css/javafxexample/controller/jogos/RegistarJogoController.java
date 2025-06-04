package pt.ul.fc.di.css.javafxexample.controller.jogos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import pt.ul.fc.di.css.javafxexample.api.ApiJogo;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.equipas.EquipaDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.JogoDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.LocalDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.MoradaDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.SelecaoDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.ArbitroDto;

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

  @FXML private ComboBox<String> horaDoJogo;

  @FXML private Text infoArbitroSelecionado;

  @FXML private Button loadJogadoresEquipa1Btn;

  @FXML private Button loadJogadoresEquipa2Btn;

  @FXML private TextField localidadeMorada;

  @FXML private ComboBox<String> minutosDoJogo;

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

  @FXML private ComboBox<EquipaDto> selecionarEquipa1Btn;

  @FXML private ComboBox<?> selecionarEquipa2Btn;

  @FXML private TableView<?> tabelaArbitros;

  @FXML private TabPane tabPane;

  @FXML private Button voltarAoMenuBtn;

  JogoDto jogoDto;
  EquipaDto equipaDto;
  SelecaoDto selecao1Dto;
  SelecaoDto selecao2Dto;
  List<ArbitroDto> arbitros;
  LocalDate dia;
  int horaInicio;
  int minutosInicio;

  @FXML
  public void initialize() {
    jogoDto = new JogoDto();
    equipaDto = new EquipaDto();
    selecao1Dto = new SelecaoDto();
    selecao2Dto = new SelecaoDto();
    arbitros = new ArrayList<>();

    bootStaticComboBoxes();
    setDefaultLocal();
  }

  private void bootStaticComboBoxes() {
    ObservableList<String> horas = FXCollections.observableArrayList();
    ObservableList<String> minutos = FXCollections.observableArrayList();
    for (int i = 0; i < 24; i++) {
      String hora = Integer.toString(i);
      horas.add((i < 10) ? '0' + hora : hora);
    }
    horaDoJogo.setItems(horas);
    horaDoJogo
        .valueProperty()
        .addListener((obs, oldVal, newVal) -> horaInicio = Integer.parseInt(newVal));

    for (int i = 0; i < 60; i++) {
      String minuto = Integer.toString(i);
      minutos.add((i < 10) ? '0' + minuto : minuto);
    }
    minutosDoJogo.setItems(minutos);
    minutosDoJogo
        .valueProperty()
        .addListener((obs, oldVal, newVal) -> minutosInicio = Integer.parseInt(newVal));

    ObservableList<EquipaDto> equipas = FXCollections.observableArrayList();
    // equipas.addAll();
    // selecionarEquipa1Btn.setItems();
  }

  private void setDefaultLocal() {
    nomeLocal.setText("Estadio1");
    proprietarioLocal.setText("Proprietario1");
    capacidadeLocal.setText("1000");
    codigoPostalMorada.setText("1111-111");
    ruaMorada.setText("Rua1");
    localidadeMorada.setText("Localidade1");
    cidadeMorada.setText("Cidade1");
    estadoMorada.setText("Estado1");
    paisMorada.setText("Pais1");
  }

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
  void passo1Avancar(ActionEvent event) {
    tabPane.getSelectionModel().selectNext();
  }

  @FXML
  void passo2Avancar(ActionEvent event) {
    tabPane.getSelectionModel().selectNext();
  }

  @FXML
  void passo2Voltar(ActionEvent event) {
    tabPane.getSelectionModel().selectPrevious();
  }

  @FXML
  void passo3Avancar(ActionEvent event) {
    tabPane.getSelectionModel().selectNext();
  }

  @FXML
  void passo3Voltar(ActionEvent event) {
    tabPane.getSelectionModel().selectPrevious();
  }

  @FXML
  void passo4Avancar(ActionEvent event) {
    tabPane.getSelectionModel().selectNext();
  }

  @FXML
  void passo4Voltar(ActionEvent event) {
    tabPane.getSelectionModel().selectPrevious();
  }

  @FXML
  void passo5Voltar(ActionEvent event) {
    tabPane.getSelectionModel().selectPrevious();
  }

  @FXML
  void removeArbitroLista(ActionEvent event) {}

  @FXML
  void verificarDisponibilidadeArbitro(ActionEvent event) {}

  @FXML
  void verificarDisponibilidadeJogs1(ActionEvent event) {}

  @FXML
  void verificarDisponibilidadeJogs2(ActionEvent event) {}

  @FXML
  void verificarDisponibilidadeLocal(ActionEvent event) {
    dia = diaDoJogo.getValue();
    jogoDto.setDiaEHora(dia.atTime(horaInicio, minutosInicio));
    String nome = nomeLocal.getText();
    String prop = proprietarioLocal.getText();
    int capacidade = Integer.parseInt(capacidadeLocal.getText());
    String codigoPostal = codigoPostalMorada.getText();
    String rua = ruaMorada.getText();
    String localidade = localidadeMorada.getText();
    String cidade = cidadeMorada.getText();
    String estado = estadoMorada.getText();
    String pais = paisMorada.getText();

    LocalDto local =
        new LocalDto(
            null,
            nome,
            prop,
            capacidade,
            new MoradaDto(codigoPostal, rua, localidade, cidade, estado, pais));
    jogoDto.setLocalDto(local);
    System.out.println(jogoDto);
    boolean disponivel = false;
    try {
      disponivel = ApiJogo.localDisponivel(jogoDto);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    disponibilidadeLocal.setText(disponivel ? "OK" : "INDISPONIVEL");
  }

  @FXML
  void voltarAoMenu(ActionEvent event) {
    Util.switchScene(
        getStage(), "/pt/ul/fc/di/css/javafxexample/view/jogos/init_jogo.fxml", "Jogos");
  }
}
