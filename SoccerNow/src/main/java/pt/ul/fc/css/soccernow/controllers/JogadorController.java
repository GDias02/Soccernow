package pt.ul.fc.css.soccernow.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;

@RestController
@RequestMapping("/api/jogadores")
@Api(value = "Jogador API", tags = "Jogadores")
public class JogadorController {
    
    @Autowired
    private JogadorHandler jogadorHandler;

    @PostMapping("/create")
    @ApiOperation(value = "Create jogador", notes = "Creates a new jogador and returns the created jogador DTO.")
    public ResponseEntity<JogadorDto> registarJogador(@RequestBody JogadorPostDto jogadorDto) {
        JogadorDto responseDto = jogadorHandler.registarJogador(jogadorDto);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{nif}")
    @ApiOperation(value = "Get jogador by NIF", notes = "Returns a jogador given its NIF.")
    public ResponseEntity<JogadorDto> verificarJogador(@PathVariable("nif") int nif) {
        JogadorDto jogadorDto = jogadorHandler.verificarJogador(nif);
        if (jogadorDto != null) {
            return ResponseEntity.ok(jogadorDto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{nif}")
    @ApiOperation(value = "Delete jogador by NIF", notes = "Deletes a jogador with a given NIF.")
    public ResponseEntity<JogadorDto> removerJogador(@PathVariable("nif") int nif) {
        jogadorHandler.removerJogador(nif);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update jogador", notes = "Updates the given jogador.")
    public ResponseEntity<JogadorDto> atualizarJogador(@RequestBody JogadorDto jogadorDto) {
        JogadorDto responseDto = jogadorHandler.atualizarJogador(jogadorDto);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @ApiOperation(value = "Get all jogadores", notes = "Returns all jogadores.")
    public ResponseEntity<Set<JogadorDto>> buscarJogadores() {
        Set<JogadorDto> jogadorDtos = jogadorHandler.buscarJogadores();
        return ResponseEntity.ok(jogadorDtos);
    }
}
