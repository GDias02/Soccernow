package pt.ul.fc.css.soccernow.thymeleafcontrollers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpSession;
import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.dto.jogos.CartaoDto;
import pt.ul.fc.css.soccernow.dto.jogos.EstatisticaJogoDto;
import pt.ul.fc.css.soccernow.dto.jogos.GoloDto;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroPostDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.entities.jogos.EstadoDeJogo;
import pt.ul.fc.css.soccernow.filtros.CartaoThymeleafDto;
import pt.ul.fc.css.soccernow.filtros.EquipaThymeleafDto;
import pt.ul.fc.css.soccernow.filtros.GoloThymeleafDto;
import pt.ul.fc.css.soccernow.filtros.JogoFiltros;
import pt.ul.fc.css.soccernow.filtros.JogoThymeleafDto;
import pt.ul.fc.css.soccernow.filtros.ResultadosThymeleafDto;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;
import pt.ul.fc.css.soccernow.handlers.CampeonatoHandler;
import pt.ul.fc.css.soccernow.handlers.EquipaHandler;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;
import pt.ul.fc.css.soccernow.handlers.JogoHandler;

@Controller
@RequestMapping("/leaf/api/jogos")
@Api(value = "Jogo API", tags = "Jogos")
public class JogoLeafController {

  @Autowired private JogoHandler jogoHandler;

  @Autowired private EquipaHandler equipaHandler;

  @Autowired private CampeonatoHandler campeonatoHandler;

  @Autowired private ArbitroHandler arbitroHandler;

  @Autowired private JogadorHandler jogadorHandler;

  private void resetSession(HttpSession session) {
    session.setAttribute("jogo", new JogoDto());
    session.setAttribute("cartoes", new HashSet<CartaoThymeleafDto>());
    session.setAttribute("cartao", new CartaoThymeleafDto());
    session.setAttribute("arbitros", new ArrayList<ArbitroPostDto>());
    session.setAttribute("jogadores", new ArrayList<JogadorDto>());
    session.setAttribute("equipas", new ArrayList<EquipaThymeleafDto>());
    session.setAttribute("horas", new ArrayList<String>());
    session.setAttribute("golos", new HashSet<GoloThymeleafDto>());
    session.setAttribute("golo", new GoloThymeleafDto());
    session.setAttribute("resultados", new ResultadosThymeleafDto());
  }

  @GetMapping("/gameover/selecionar/start")
  @ApiOperation(value = "Indica que um jogo terminou", notes = "Seleciona um jogo para terminar.")
  public String endJogoSelecionarStart(final HttpSession session) {
    List<String> minutos = new ArrayList<>();
    for (int i = 0; i < 60; i++) {
      String minuto = Integer.toString(i);
      minutos.add((i < 10) ? '0' + minuto : minuto);
    }

    Set<JogoDto> jogos = jogoHandler.buscarJogos();
    Set<JogoThymeleafDto> jogosTL = new HashSet<>();
    for (JogoDto jogo : jogos) {
      if (jogo.getEstadoDeJogo().equals(EstadoDeJogo.TERMINADO) || jogo.getEstadoDeJogo().equals(EstadoDeJogo.CANCELADO)) continue;
      JogoThymeleafDto jogoTL = new JogoThymeleafDto();
      jogoTL.setId(jogo.getId());
      jogoTL.setDiaEHora(jogo.getDiaEHora());
      jogoTL.setLocalDto(jogo.getLocalDto());
      jogoTL.setEquipaNome1(equipaHandler.verificarEquipa(jogo.getS1().getEquipa()).getNome());
      jogoTL.setEquipaNome2(equipaHandler.verificarEquipa(jogo.getS2().getEquipa()).getNome());
      if (jogo.getCampeonato() == null) jogoTL.setCampeonatoTipo("Amigável");
      else jogoTL.setCampeonatoTipo("Campeonato");
      jogosTL.add(jogoTL);
    }

    session.setAttribute("jogos", jogosTL);
    session.setAttribute("minutos", minutos);
    resetSession(session);
    return "jogo_gameover_selecionar";
  }

  @GetMapping("/gameover/selecionar")
  @ApiOperation(value = "Indica que um jogo terminou", notes = "Apresenta uma lista de jogos por terminar.")
  public String endJogoSelecionar(final HttpSession session) {

    return "jogo_gameover_selecionar";
  }

  @GetMapping("/gameover/selecionar/{id}")
  @ApiOperation(value = "Indica que um jogo terminou", notes = "Seleciona um jogo para terminar.")
  public String endJogoSelecionarCreate(
    final Model model,
    final HttpSession session, 
    @PathVariable("id") Long id) {

    resetSession(session);

    session.setAttribute("jogo", jogoHandler.getJogo(id));
    JogoDto jogo = (JogoDto) session.getAttribute("jogo");

    session.setAttribute("arbitros", jogo.getEquipaDeArbitros());

    List<JogadorDto> jogadores = (List<JogadorDto>) session.getAttribute("jogadores");
    List<JogadorDto> jogadores1 = jogoHandler.buscarJogadoresDeSelecao(jogo.getS1().getId());
    List<JogadorDto> jogadores2 = jogoHandler.buscarJogadoresDeSelecao(jogo.getS2().getId());
    jogadores.removeAll(jogadores);
    jogadores.addAll(jogadores1);
    jogadores.addAll(jogadores2);
    session.setAttribute("jogadores", jogadores);

    List<EquipaThymeleafDto> equipas = (List<EquipaThymeleafDto>) session.getAttribute("equipas");
    EquipaDto equipa1 = equipaHandler.verificarEquipa(jogo.getS1().getEquipa());
    EquipaDto equipa2 = equipaHandler.verificarEquipa(jogo.getS2().getEquipa());
    equipas.add(new EquipaThymeleafDto(equipa1.getId(), equipa1.getNome()));
    equipas.add(new EquipaThymeleafDto(equipa2.getId(), equipa2.getNome()));
    session.setAttribute("equipas", equipas);
    
    List<String> horas = (List<String>) session.getAttribute("horas");
    horas.removeAll(horas);
    LocalDateTime inicioJogo = jogo.getDiaEHora();
    horas.add(Integer.toString(inicioJogo.getHour()));
    horas.add(Integer.toString(inicioJogo.plusHours(1).getHour()));
    horas.add(Integer.toString(inicioJogo.plusHours(2).getHour()));
    session.setAttribute("horas", horas);

    model.addAttribute("cartao", (CartaoThymeleafDto) session.getAttribute("cartao"));

    return "jogo_gameover_cartoes";
  }

  @GetMapping("/gameover/cartoes")
  @ApiOperation(value = "Indica que um jogo terminou", notes = "Adiciona os cartões mostrados no jogo.")
  public String endJogoCartoes(
    final Model model,
    final HttpSession session) {

    model.addAttribute("cartao", (CartaoThymeleafDto) session.getAttribute("cartao"));

    return "jogo_gameover_cartoes";
  }

  @PostMapping("/gameover/cartoes/create")
  @ApiOperation(value = "Indica que um jogo terminou", notes = "Adiciona um cartão mostrado no jogo.")
  public String endJogoCartoesCreate(
    @ModelAttribute CartaoThymeleafDto cartao,
    final Model model,
    final HttpSession session) {

    JogoDto jogo = (JogoDto) session.getAttribute("jogo");

    try {
      cartao.setArbitroNome(arbitroHandler.verificarArbitroPorId(cartao.getArbitro()).getUtilizador().getNome());
      cartao.setAtribuidoANome(jogadorHandler.verificarJogadorPorId(cartao.getAtribuidoA()).getUtilizador().getNome());
      List<JogadorDto> jogadores1 = jogoHandler.buscarJogadoresDeSelecao(jogo.getS1().getId());
      List<JogadorDto> jogadores2 = jogoHandler.buscarJogadoresDeSelecao(jogo.getS2().getId());
      for (JogadorDto jogador : jogadores1) {
        if (jogador.getUtilizador().getId().equals(cartao.getAtribuidoA())) {
          cartao.setEquipaNome(equipaHandler.verificarEquipa(jogo.getS1().getEquipa()).getNome());
          break;
        }
      }
      if (cartao.getEquipaNome() == null) {
        for (JogadorDto jogador : jogadores2) {
          if (jogador.getUtilizador().getId().equals(cartao.getAtribuidoA())) {
            cartao.setEquipaNome(equipaHandler.verificarEquipa(jogo.getS2().getEquipa()).getNome());
            break;
          }
        }
      }
    } catch (Exception e) {
      //Do Nothing
    }

    session.setAttribute("cartao", cartao);

    CartaoThymeleafDto cartaoSession = (CartaoThymeleafDto) session.getAttribute("cartao");
    Set<CartaoThymeleafDto> cartoes = (Set<CartaoThymeleafDto>) session.getAttribute("cartoes");
    if (jogo.getS1().getJogadoresIds().contains(cartaoSession.getAtribuidoA())) {
      cartaoSession.setEquipa(jogoHandler.getEquipaFromSelecao(jogo.getS1()).getId());
    }
    else if (jogo.getS2().getJogadoresIds().contains(cartaoSession.getAtribuidoA())) {
      cartaoSession.setEquipa(jogoHandler.getEquipaFromSelecao(jogo.getS2()).getId());
    }
    session.setAttribute("cartao", cartaoSession);
    cartoes.add(cartaoSession);
    session.setAttribute("cartoes", cartoes);
    model.addAttribute("cartao", cartaoSession);
    return "jogo_gameover_cartoes";
  }

  @GetMapping("/gameover/golos")
  @ApiOperation(value = "Indica que um jogo terminou", notes = "Adiciona os golos marcados no jogo.")
  public String endJogoGolos(
    final Model model,
    final HttpSession session) {

    model.addAttribute("golo", (GoloThymeleafDto) session.getAttribute("golo"));

    return "jogo_gameover_golos";
  }

  @PostMapping("/gameover/golos/create")
  @ApiOperation(value = "Indica que um jogo terminou", notes = "Adiciona um golo marcado no jogo.")
  public String endJogoGolosCreate(
    @ModelAttribute GoloThymeleafDto golo,
    final Model model,
    final HttpSession session) {

    JogoDto jogo = (JogoDto) session.getAttribute("jogo");
    List<EquipaThymeleafDto> equipas = (List<EquipaThymeleafDto>) session.getAttribute("equipas");

    try {
      golo.setMarcadorNome(jogadorHandler.verificarJogadorPorId(golo.getMarcador()).getUtilizador().getNome());
      golo.setEquipaNome(golo.getEquipa().equals(equipas.get(0).getId()) ? equipas.get(0).getNome() : equipas.get(1).getNome());
    } catch (Exception e) {
      //Do Nothing
    }

    session.setAttribute("golo", golo);
    Set<GoloThymeleafDto> golos = (Set<GoloThymeleafDto>) session.getAttribute("golos");
    golos.add(golo);
    session.setAttribute("golos", golos);
    model.addAttribute("golo", golo);
    return "jogo_gameover_golos";
  }

  @GetMapping("/gameover/finalizar")
  @ApiOperation(value = "Indica que um jogo terminou", notes = "Termina efetivamente o jogo")
  public String endJogoFinalizar(final HttpSession session) {

    ResultadosThymeleafDto resultados = (ResultadosThymeleafDto) session.getAttribute("resultados");
    int score1 = 0;
    int score2 = 0;
    List<EquipaThymeleafDto> equipas = (List<EquipaThymeleafDto>) session.getAttribute("equipas");
    String equipa1 = equipas.get(0).getNome();
    String equipa2 = equipas.get(1).getNome();
    String equipaVencedora = "Empate";

    for (GoloThymeleafDto golo : (Set<GoloThymeleafDto>) session.getAttribute("golos")) {
      if (golo.getEquipaNome().equals(equipa1)) score1++;
      else if (golo.getEquipaNome().equals(equipa2)) score2++;
    }

    if (score1 > score2) equipaVencedora = equipa1;
    else if (score2 > score1) equipaVencedora = equipa2;

    resultados.setPlacar(equipa1 + ": " + score1 + "; " + equipa2 + ": " + score2);
    resultados.setEquipaVencedora(equipaVencedora);

    return "jogo_gameover_finalizar";
  }

  @PostMapping("/gameover/finalizar/update")
  @ApiOperation(value = "Indica que um jogo terminou", notes = "Termina efetivamente o jogo")
  public String endJogoFinalizarUpdate(final Model model, final HttpSession session) {

    JogoDto jogo = (JogoDto) session.getAttribute("jogo");
    LocalDateTime time = jogo.getDiaEHora();

    EstatisticaJogoDto ej = new EstatisticaJogoDto();

    Set<CartaoDto> cartoesDto = new HashSet<>();
    for (CartaoThymeleafDto cartaoTL : (Set<CartaoThymeleafDto>) session.getAttribute("cartoes")) {
      CartaoDto cartao = new CartaoDto();
      cartao.setQuando(time.withHour(cartaoTL.getHoras()).withMinute(cartaoTL.getMinutos()));
      cartao.setJogo(jogo.getId());
      cartao.setAtribuidoA(cartaoTL.getAtribuidoA());
      cartao.setArbitro(cartaoTL.getArbitro());
      cartao.setEquipa(cartaoTL.getEquipa());
      cartao.setCor(cartaoTL.getCor());
      cartoesDto.add(cartao);
    }

    Set<GoloDto> golosDto = new HashSet<>();
    for (GoloThymeleafDto goloTL : (Set<GoloThymeleafDto>) session.getAttribute("golos")) {
      GoloDto golo = new GoloDto();
      golo.setQuando(time.withHour(goloTL.getHoras()).withMinute(goloTL.getMinutos()));
      golo.setJogo(jogo.getId());
      golo.setMarcador(goloTL.getMarcador());
      golo.setEquipa(goloTL.getEquipa());
      golosDto.add(golo);
    }

    ej.setCartoes(cartoesDto);
    ej.setGolos(golosDto);

    JogoDto fimDoJogo = new JogoDto();
    fimDoJogo.setId(jogo.getId());
    fimDoJogo.setEstadoDeJogo(EstadoDeJogo.TERMINADO);
    fimDoJogo.setStats(ej);

    jogoHandler.registarResultadoDeJogo(fimDoJogo);

    model.addAttribute("jogos", jogoHandler.buscarJogos());
    model.addAttribute("filtros", new JogoFiltros());

    return "jogo_lista";
  }

  @GetMapping
  @ApiOperation(value = "Get all jogos", notes = "Returns all jogos.")
  public String buscarJogos(final Model model) {
    model.addAttribute("jogos", jogoHandler.buscarJogos());
    model.addAttribute("filtros", new JogoFiltros());
    return "jogo_lista";
  }

  @GetMapping("/jogo")
  @ApiOperation(value = "Get filtered jogos", notes = "Returns jogos that match the given filter.")
  public String filtrarJogos(final Model model,
    @RequestParam(name="jogosEstado", required=false) String jogosEstado,
    @RequestParam(name="golos", required=false) String golos,
    @RequestParam(name="local", required=false) String local,
    @RequestParam(name="turno", required=false) String turno) { 

    model.addAttribute("jogos", jogoHandler.filtrarJogos(jogosEstado, golos, local, turno));
    model.addAttribute("filtros", new JogoFiltros(jogosEstado, golos, local, turno));
    return "jogo_lista";
  }
}
