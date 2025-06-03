package pt.ul.fc.css.soccernow.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.handlers.JogoHandler;

@RestController
@RequestMapping("/api/jogos")
@Api(value = "Jogo API", tags = "Jogos")
public class JogoController {

  @Autowired private JogoHandler jogoHandler;

  @GetMapping("/{id}")
  @ApiOperation(
      value = "Obtem um jogo particular",
      notes = "Devolve o jogo com este id, e os seus atributos relevantes.")
  public ResponseEntity<JogoDto> getJogo(@PathVariable("id") long id) {
    JogoDto jogoDto = jogoHandler.getJogo(id);
    if (jogoDto != null) {
      return ResponseEntity.ok(jogoDto);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/create")
  @ApiOperation(
      value = "Cria um jogo novo",
      notes =
          "Cria um jogo novo agendado para a data fornecida. Fornecer a data e o horario, o arbitro"
              + " principal e as duas selecoes no request body.")
  public ResponseEntity<JogoDto> createJogo(@RequestBody JogoDto jogo) {
    System.out.println(jogo);
    JogoDto jogoDto = jogoHandler.createJogo(jogo);
    if (jogoDto != null) return ResponseEntity.ok(jogoDto);
    else return ResponseEntity.badRequest().build();
  }

  @PutMapping("/gameover")
  @ApiOperation(
      value = "Indica que um jogo terminou",
      notes =
          "Termina um jogo, recorrendo ao método de updateJogo. Fornecer o id do jogo no path,"
              + " assim como o estado do jogo respetivo e as estatisticas relevantes no request"
              + " body")
  public ResponseEntity<JogoDto> endJogo(@RequestBody JogoDto jogo) {
    JogoDto jogoDto = jogoHandler.registarResultadoDeJogo(jogo);
    if (jogoDto != null) return ResponseEntity.ok(jogoDto);
    else return ResponseEntity.badRequest().build();
  }

  @PutMapping("/update/{jogoId}")
  @ApiOperation(
      value = "Altera as condições de um jogo",
      notes =
          "Muda qualquer parametro de um jogo. Fornecer o id do jogo no path, assim como o estado"
              + " do jogo respetivo e as estatisticas relevantes no request body")
  public ResponseEntity<JogoDto> updateJogo(
      @PathVariable("jogoId") long jogoId, @RequestBody JogoDto jogo) {
    JogoDto jogoDto = jogoHandler.updateJogo(jogoId, jogo);
    if (jogoDto != null) return ResponseEntity.ok(jogoDto);
    else return ResponseEntity.badRequest().build();
  }

  @GetMapping
  @ApiOperation(value = "Get all jogos", notes = "Returns all jogos.")
  public ResponseEntity<Set<JogoDto>> buscarJogos() {
    Set<JogoDto> jogosDtos = jogoHandler.buscarJogos();
    return ResponseEntity.ok(jogosDtos);
  }

  @PostMapping("/local/available")
  @ApiOperation(value = "Verifica a disponibilidade de um local", notes = "True se disponivel")
  public ResponseEntity<Boolean> localDisponivel(@RequestBody JogoDto jogo) {
    Boolean disponivel = jogoHandler.localDisponivel(jogo);
    return ResponseEntity.ok(disponivel);
  }

  @PostMapping("/arbitros/available")
  @ApiOperation(
      value = "Verifica a disponibilidade de uma lista de arbitros",
      notes = "True se todos disponiveis")
  public ResponseEntity<Boolean> arbitrosDisponiveis(@RequestBody JogoDto jogo) {
    Boolean disponivel = jogoHandler.arbitrosDisponiveis(jogo);
    return ResponseEntity.ok(disponivel);
  }

  @PostMapping("jogadores/available")
  @ApiOperation(
      value = "Verifica a disponibilidade dos jogadores nas selecoes",
      notes = "True se todos disponiveis")
  public ResponseEntity<Boolean> jogadoresDisponiveis(@RequestBody JogoDto jogo) {
    Boolean disponivel = jogoHandler.jogadoresDisponiveis(jogo);
    return ResponseEntity.ok(disponivel);
  }
}
