package pt.ul.fc.css.soccernow.thymeleafcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.filtros.JogoFiltros;
import pt.ul.fc.css.soccernow.handlers.JogoHandler;

@Controller
@RequestMapping("/leaf/api/jogos")
@Api(value = "Jogo API", tags = "Jogos")
public class JogoLeafController {

  @Autowired private JogoHandler jogoHandler;

  /* @PutMapping("/gameover")
  @ApiOperation(
      value = "Indica que um jogo terminou",
      notes =
          "Termina um jogo, recorrendo ao método de updateJogo. Fornecer o id do jogo no path,"
              + " assim como o estado do jogo respetivo e as estatisticas relevantes no request"
              + " body")
  public String endJogo(@RequestBody JogoDto jogo) {
    JogoDto jogoDto = jogoHandler.registarResultadoDeJogo(jogo);
    if (jogoDto != null) return ResponseEntity.ok(jogoDto);
    else return ResponseEntity.badRequest().build();
  } */

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
