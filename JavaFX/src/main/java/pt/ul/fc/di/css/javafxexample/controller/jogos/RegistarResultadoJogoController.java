package pt.ul.fc.di.css.javafxexample.controller.jogos;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import pt.ul.fc.di.css.javafxexample.api.ApiEquipa;
import pt.ul.fc.di.css.javafxexample.api.ApiJogo;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.jogos.CartaoDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.Cor;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstadoDeJogo;
import pt.ul.fc.di.css.javafxexample.dto.jogos.EstatisticaJogoDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.GoloDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.JogoDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.SelecaoDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;

public class RegistarResultadoJogoController extends Controller {

  @FXML private Text equipaVencedoraDisplay;
  @FXML private Button passo1AvancarBtn1;
  @FXML private Button passo2AvancarBtn;
  @FXML private Button passo2VoltarBtn;
  @FXML private Button passo3AvancarBtn;
  @FXML private Button passo3VoltarBtn;
  @FXML private Button passo4VoltarBtn;
  @FXML private Text placarDisplay;
  @FXML private Button resultadoJogoSubmitBtn;
  @FXML private ComboBox<ArbitroDto> selecionarArbitro;
  @FXML private ComboBox<Cor> selecionarCor;
  @FXML private ComboBox<SelecaoDto> selecionarEquipa;
  @FXML private ComboBox<String> selecionarHoraCartao;
  @FXML private ComboBox<String> selecionarHoraGolo;
  @FXML private ComboBox<JogadorDto> selecionarJogador;
  @FXML private ComboBox<JogadorDto> selecionarMarcador;
  @FXML private ComboBox<String> selecionarMinutoCartao;
  @FXML private ComboBox<String> selecionarMinutoGolo;
  @FXML private TabPane tabPane;
  @FXML private TableView<CartaoDto> tabelaCartoes;
  @FXML private TableColumn<CartaoDto, String> cartaoArbitro;
  @FXML private TableColumn<CartaoDto, String> cartaoCor;
  @FXML private TableColumn<CartaoDto, String> cartaoEquipa;
  @FXML private TableColumn<CartaoDto, String> cartaoJogador;
  @FXML private TableColumn<CartaoDto, String> cartaoTempo;
  @FXML private TableView<GoloDto> tabelaGolos;
  @FXML private TableColumn<GoloDto, String> goloEquipa;
  @FXML private TableColumn<GoloDto, String> goloJogador;
  @FXML private TableColumn<GoloDto, String> goloTempo;
  @FXML private TableView<JogoDto> tabelaJogos;
  @FXML private TableColumn<JogoDto, String> jogoCampeonato;
  @FXML private TableColumn<JogoDto, String> jogoData;
  @FXML private TableColumn<JogoDto, String> jogoEquipa1;
  @FXML private TableColumn<JogoDto, String> jogoEquipa2;
  @FXML private TableColumn<JogoDto, String> jogoId;
  @FXML private TableColumn<JogoDto, String> jogoLocal;
  @FXML private Button voltarAoMenuBtn;

  JogoDto jogoSelecionado;
  Map<Long, String> selecoesDeEquipa;

  StringConverter<JogadorDto> converterJogadores =
      new StringConverter<>() {
        @Override
        public String toString(JogadorDto e) {
          return (e != null && e.getUtilizador().getNome() != null)
              ? e.getUtilizador().getNome()
              : "";
        }

        @Override
        public JogadorDto fromString(String string) {
          return null;
        }
      };

  StringConverter<ArbitroDto> converterArbitros =
      new StringConverter<>() {
        @Override
        public String toString(ArbitroDto e) {
          return (e != null && e.getUtilizador().getNome() != null)
              ? e.getUtilizador().getNome()
              : "";
        }

        @Override
        public ArbitroDto fromString(String string) {
          return null;
        }
      };

  StringConverter<SelecaoDto> converterSelecoes =
      new StringConverter<>() {
        @Override
        public String toString(SelecaoDto e) {
          return (e != null && e.getEquipa() != null) ? selecoesDeEquipa.get(e.getEquipa()) : "";
        }

        @Override
        public SelecaoDto fromString(String string) {
          return null;
        }
      };

  @FXML
  public void initialize() {

    setupTabelaJogos();
    setupTabelaCartoes();
    setupTabelaGolos();

    selecoesDeEquipa = new HashMap<>();
    ObservableList<Cor> cores = FXCollections.observableArrayList(Cor.values());
    selecionarCor.setItems(cores);
  }

  String findNomeEquipa(Long id) {
    try {
      return ApiEquipa.buscarEquipaPorId(id).getNome();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    return "<sem nome>";
  }

  String matchNomeEquipa(Long id) {
    if (selecoesDeEquipa.isEmpty()) return "<sem nome>";
    if (selecoesDeEquipa.containsKey(id)) {
      return selecoesDeEquipa.get(id);
    }
    return "<sem nome>";
  }

  String matchNomeJogador(Long id) {
    if (selecionarJogador.getItems().isEmpty()) return "<sem nome>";
    List<JogadorDto> jogadores = selecionarJogador.getItems();
    for (JogadorDto j : jogadores) {
      if (j.getUtilizador().getId().equals(id)) {
        return j.getUtilizador().getNome();
      }
    }
    return "<sem nome>";
  }

  String findNomeArbitro(Long id) {
    if (jogoSelecionado == null) return "<sem nome>";
    List<ArbitroDto> arbitrosDisp = jogoSelecionado.getEquipaDeArbitros();
    for (ArbitroDto a : arbitrosDisp) {
      if (a.getUtilizador().getId().equals(id)) {
        return a.getUtilizador().getNome();
      }
    }
    return "<sem nome>";
  }

  void setupTabelaJogos() {
    jogoId.setCellValueFactory(
        cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
    jogoLocal.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getLocalDto().getNome()));
    jogoData.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getDiaEHora().toString()));
    jogoEquipa1.setCellValueFactory(
        cellData ->
            new SimpleStringProperty(findNomeEquipa(cellData.getValue().getS1().getEquipa())));
    jogoEquipa2.setCellValueFactory(
        cellData ->
            new SimpleStringProperty(findNomeEquipa(cellData.getValue().getS2().getEquipa())));
    jogoCampeonato.setCellValueFactory(
        cellData ->
            new SimpleStringProperty(
                (cellData.getValue().getCampeonato() != null
                        && cellData.getValue().getCampeonato() > 0L)
                    ? String.valueOf(cellData.getValue().getCampeonato())
                    : "amigável"));

    ObservableList<JogoDto> jogos = FXCollections.observableArrayList();
    try {
      jogos.addAll(ApiJogo.getAllJogos());
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    tabelaJogos.setItems(jogos);
  }

  void setupTabelaCartoes() {
    cartaoArbitro.setCellValueFactory(
        cellData ->
            new SimpleStringProperty(
                String.valueOf(findNomeArbitro(cellData.getValue().getArbitro()))));
    cartaoCor.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getCor().toString()));
    cartaoEquipa.setCellValueFactory(
        cellData -> new SimpleStringProperty(matchNomeEquipa(cellData.getValue().getEquipa())));
    cartaoJogador.setCellValueFactory(
        cellData ->
            new SimpleStringProperty(matchNomeJogador(cellData.getValue().getAtribuidoA())));
    cartaoTempo.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getQuando().toString()));

    ObservableList<CartaoDto> cartoes = FXCollections.observableArrayList();
    tabelaCartoes.setItems(cartoes);
  }

  void setupTabelaGolos() {
    goloEquipa.setCellValueFactory(
        cellData -> new SimpleStringProperty(matchNomeEquipa(cellData.getValue().getEquipa())));
    goloJogador.setCellValueFactory(
        cellData -> new SimpleStringProperty(matchNomeJogador(cellData.getValue().getMarcador())));
    goloTempo.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getQuando().toString()));

    ObservableList<GoloDto> golos = FXCollections.observableArrayList();
    tabelaGolos.setItems(golos);
  }

  boolean selectJogo() {
    jogoSelecionado = tabelaJogos.getSelectionModel().getSelectedItem();
    if (jogoSelecionado != null) {
      int i = tabelaJogos.getSelectionModel().getSelectedIndex();
      selecoesDeEquipa.put(jogoSelecionado.getS1().getEquipa(), jogoEquipa1.getCellData(i));
      selecoesDeEquipa.put(jogoSelecionado.getS2().getEquipa(), jogoEquipa2.getCellData(i));
      bootComboBoxes();
      return true;
    }
    return false;
  }

  void bootComboBoxes() {
    SelecaoDto s1 = jogoSelecionado.getS1();
    SelecaoDto s2 = jogoSelecionado.getS2();

    ObservableList<SelecaoDto> equipas = FXCollections.observableArrayList();
    equipas.addAll(s1, s2);

    selecionarEquipa.setItems(equipas);
    selecionarEquipa.setConverter(converterSelecoes);

    ObservableList<JogadorDto> jogadores = FXCollections.observableArrayList();
    try {
      jogadores.addAll(ApiJogo.jogadoresDeSelecao(s1));
      jogadores.addAll(ApiJogo.jogadoresDeSelecao(s2));
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    selecionarJogador.setItems(jogadores);
    selecionarJogador.setConverter(converterJogadores);

    selecionarMarcador.setItems(jogadores);
    selecionarMarcador.setConverter(converterJogadores);

    ObservableList<ArbitroDto> arbitros =
        FXCollections.observableArrayList(jogoSelecionado.getEquipaDeArbitros());
    selecionarArbitro.setItems(arbitros);
    selecionarArbitro.setConverter(converterArbitros);

    LocalDateTime inicioJogo = jogoSelecionado.getDiaEHora();

    ObservableList<String> horas = FXCollections.observableArrayList();
    ObservableList<String> minutos = FXCollections.observableArrayList();

    horas.add(Integer.toString(inicioJogo.getHour()));
    horas.add(Integer.toString(inicioJogo.plusHours(1).getHour()));
    horas.add(Integer.toString(inicioJogo.plusHours(2).getHour()));

    selecionarHoraCartao.setItems(horas);
    selecionarHoraGolo.setItems(horas);

    for (int i = 0; i < 60; i++) {
      String minuto = Integer.toString(i);
      minutos.add((i < 10) ? '0' + minuto : minuto);
    }
    selecionarMinutoCartao.setItems(minutos);
    selecionarMinutoGolo.setItems(minutos);
  }

  @FXML
  void addCartaoLista(ActionEvent event) {
    Long currentArbitro =
        selecionarArbitro.getSelectionModel().getSelectedItem().getUtilizador().getId();
    if (currentArbitro == null || currentArbitro < 1) {
      return;
    }
    Long currentJogador =
        selecionarJogador.getSelectionModel().getSelectedItem().getUtilizador().getId();
    if (currentJogador == null || currentJogador < 1) {
      return;
    }
    Set<Long> s1 = jogoSelecionado.getS1().getJogadoresIds();
    Long currentEquipa =
        s1.contains(currentJogador)
            ? jogoSelecionado.getS1().getEquipa()
            : jogoSelecionado.getS2().getEquipa();
    if (currentEquipa == null || currentEquipa < 1) {
      return;
    }
    Cor currentCor = selecionarCor.getSelectionModel().getSelectedItem();
    if (currentCor == null) {
      return;
    }
    if (selecionarHoraCartao.getSelectionModel().getSelectedItem() == null) {
      return;
    }
    if (selecionarMinutoCartao.getSelectionModel().getSelectedItem() == null) {
      return;
    }

    int currentHora = Integer.parseInt(selecionarHoraCartao.getSelectionModel().getSelectedItem());
    int currentMinuto =
        Integer.parseInt(selecionarMinutoCartao.getSelectionModel().getSelectedItem());
    LocalDateTime diaEHora =
        jogoSelecionado.getDiaEHora().withHour(currentHora).withMinute(currentMinuto);

    CartaoDto novoCartao =
        new CartaoDto(
            diaEHora,
            jogoSelecionado.getId(),
            currentJogador,
            currentArbitro,
            currentEquipa,
            currentCor);

    ObservableList<CartaoDto> a = tabelaCartoes.getItems();
    if (!a.contains(novoCartao)) {
      a.add(novoCartao);
    }
    tabelaCartoes.setItems(a);
  }

  @FXML
  void addGoloLista(ActionEvent event) {
    Long currentJogador =
        selecionarMarcador.getSelectionModel().getSelectedItem().getUtilizador().getId();
    if (currentJogador == null || currentJogador < 1) {
      return;
    }
    Long currentEquipa = selecionarEquipa.getSelectionModel().getSelectedItem().getEquipa();

    if (selecionarHoraGolo.getSelectionModel().getSelectedItem() == null) {
      return;
    }
    if (selecionarMinutoGolo.getSelectionModel().getSelectedItem() == null) {
      return;
    }

    int currentHora = Integer.parseInt(selecionarHoraGolo.getSelectionModel().getSelectedItem());
    int currentMinuto =
        Integer.parseInt(selecionarMinutoGolo.getSelectionModel().getSelectedItem());
    LocalDateTime diaEHora =
        jogoSelecionado.getDiaEHora().withHour(currentHora).withMinute(currentMinuto);

    GoloDto novoGolo =
        new GoloDto(diaEHora, jogoSelecionado.getId(), currentJogador, currentEquipa);

    ObservableList<GoloDto> a = tabelaGolos.getItems();
    if (!a.contains(novoGolo)) {
      a.add(novoGolo);
    }
    tabelaGolos.setItems(a);
  }

  @FXML
  void passo1Avancar(ActionEvent event) {
    if (!selectJogo()) return;
    tabPane.getSelectionModel().selectNext();
  }

  @FXML
  void passo2Avancar(ActionEvent event) {
    if (jogoSelecionado == null) {
      return;
    }
    tabPane.getSelectionModel().selectNext();
  }

  @FXML
  void passo2Voltar(ActionEvent event) {
    tabPane.getSelectionModel().selectPrevious();
  }

  @FXML
  void passo3Avancar(ActionEvent event) {
    if (jogoSelecionado == null) {
      return;
    }
    displaySimulacao();
    tabPane.getSelectionModel().selectNext();
  }

  @FXML
  void passo3Voltar(ActionEvent event) {
    tabPane.getSelectionModel().selectPrevious();
  }

  @FXML
  void passo4Voltar(ActionEvent event) {
    tabPane.getSelectionModel().selectPrevious();
  }

  @FXML
  void removeCartaoLista(ActionEvent event) {
    int i = tabelaCartoes.getSelectionModel().getSelectedIndex();
    if (i >= 0) {
      tabelaCartoes.getItems().remove(i);
    }
  }

  @FXML
  void removeGoloLista(ActionEvent event) {
    int i = tabelaGolos.getSelectionModel().getSelectedIndex();
    if (i >= 0) {
      tabelaGolos.getItems().remove(i);
    }
  }

  @FXML
  void resultadoJogoSubmit(ActionEvent event) {
    EstatisticaJogoDto ej = new EstatisticaJogoDto();
    ej.setCartoes(new HashSet<CartaoDto>(tabelaCartoes.getItems()));
    ej.setGolos(new HashSet<GoloDto>(tabelaGolos.getItems()));
    System.out.println(ej.getCartoes());
    JogoDto fimDoJogo = new JogoDto();
    fimDoJogo.setId(jogoSelecionado.getId());
    fimDoJogo.setEstadoDeJogo(EstadoDeJogo.TERMINADO);
    fimDoJogo.setStats(ej);

    try {
      ApiJogo.finalizarJogo(fimDoJogo);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    voltarAoMenu(event);
  }

  void displaySimulacao() {
    List<GoloDto> golos = tabelaGolos.getItems();
    int pontuacao1 = 0;
    int pontuacao2 = 0;

    for (GoloDto g : golos) {
      if (g.getEquipa().equals(jogoSelecionado.getS1().getEquipa())) {
        pontuacao1++;
      } else {
        pontuacao2++;
      }
    }
    placarDisplay.setText(pontuacao1 + " - " + pontuacao2);
    if (pontuacao1 > pontuacao2) {
      equipaVencedoraDisplay.setText(selecoesDeEquipa.get(jogoSelecionado.getS1().getEquipa()));
    } else if (pontuacao2 > pontuacao1) {
      equipaVencedoraDisplay.setText(selecoesDeEquipa.get(jogoSelecionado.getS2().getEquipa()));
    } else {
      equipaVencedoraDisplay.setText("Empate");
    }
  }

  @FXML
  void voltarAoMenu(ActionEvent event) {
    Util.switchScene(
        getStage(), "/pt/ul/fc/di/css/javafxexample/view/jogos/init_jogo.fxml", "Jogos");
  }
}
