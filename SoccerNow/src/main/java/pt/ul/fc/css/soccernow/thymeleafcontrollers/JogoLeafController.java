package pt.ul.fc.css.soccernow.thymeleafcontrollers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.handlers.JogoHandler;

@Controller
@RequestMapping("/leaf/api/jogos")
@Api(value = "Jogo API", tags = "Jogos")
public class JogoLeafController {

  @Autowired private JogoHandler jogoHandler;

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

  @GetMapping
  @ApiOperation(value = "Get all jogos", notes = "Returns all jogos.")
  public ResponseEntity<Set<JogoDto>> buscarJogos() {
    Set<JogoDto> jogosDtos = jogoHandler.buscarJogos();
    return ResponseEntity.ok(jogosDtos);
  }
}
