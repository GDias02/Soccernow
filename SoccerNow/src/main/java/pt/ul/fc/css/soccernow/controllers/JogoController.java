package pt.ul.fc.css.soccernow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.dto.jogos.JogoDto;
import pt.ul.fc.css.soccernow.handlers.JogoHandler;



@RestController
@RequestMapping("/api/jogos")
@Api(value = "Jogo API", tags = "Jogos")
public class JogoController {

    @Autowired
    private JogoHandler jogoHandler;

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtem um jogo particular", notes = "Devolve o jogo com este id, e os seus atributos relevantes.")
    public ResponseEntity<JogoDto> getJogo(@PathVariable("id") long id) {
        JogoDto jogoDto = jogoHandler.getJogo(id);
        if (jogoDto != null) {
            return ResponseEntity.ok(jogoDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    @ApiOperation(value = "Cria um jogo novo", notes = "Cria um jogo novo agendado para a data fornecida. Fornecer a data e o horario, o arbitro principal e as duas selecoes no request body.")
    public ResponseEntity<JogoDto> createJogo(
        @RequestBody JogoDto jogo) {
        JogoDto jogoDto = jogoHandler.createJogo(jogo);
        return ResponseEntity.ok(jogoDto);
    }

    @PutMapping("/finalize/{jogoId}")
    @ApiOperation(value = "Indica que um jogo terminou", notes = "Termina um jogo, recorrendo ao método de updateJogo. Fornecer o id do jogo no path, assim como o estado do jogo respetivo e as estatisticas relevantes no request body")
    public ResponseEntity<JogoDto> endJogo(
        @PathVariable("jogoId") long jogoId,
        @RequestBody JogoDto jogo) {
        JogoDto jogoDto = jogoHandler.updateJogo(jogoId, jogo);
        return ResponseEntity.ok(jogoDto);
    }

    @PutMapping("/update/{jogoId}")
    @ApiOperation(value = "Altera as condições de um jogo", notes = "Muda qualquer parametro de um jogo. Fornecer o id do jogo no path, assim como o estado do jogo respetivo e as estatisticas relevantes no request body")
    public ResponseEntity<JogoDto> updateJogo(
        @PathVariable("jogoId") long jogoId,
        @RequestBody JogoDto jogo) {
        JogoDto jogoDto = jogoHandler.updateJogo(jogoId, jogo);
        return ResponseEntity.ok(jogoDto);
    }
}