package pt.ul.fc.css.soccernow.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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
import pt.ul.fc.css.soccernow.exceptions.utilizadores.AtualizarJogadorException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.NotFoundException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RegistarJogadorException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RemoverJogadorException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.VerificarJogadorException;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;

@RestController
@RequestMapping("/api/jogadores")
@Api(value = "Jogador API", tags = "Jogadores")
public class JogadorController {
    
    @Autowired
    private JogadorHandler jogadorHandler;

    @PostMapping("/create")
    @ApiOperation(value = "Create jogador", notes = "Creates a new jogador and returns the created jogador DTO.")
    public ResponseEntity registarJogador(@RequestBody JogadorPostDto jogadorDto) {
        try {
            JogadorDto responseDto = jogadorHandler.registarJogador(jogadorDto);
            return ResponseEntity.ok(responseDto);
        } catch (RegistarJogadorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{nif}")
    @ApiOperation(value = "Get jogador by NIF", notes = "Returns a jogador given its NIF.")
    public ResponseEntity verificarJogador(@PathVariable("nif") int nif) {
        try {
            JogadorDto jogadorDto = jogadorHandler.verificarJogador(nif);
            return ResponseEntity.ok(jogadorDto);
        } catch (VerificarJogadorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @DeleteMapping("/{nif}")
    @ApiOperation(value = "Delete jogador by NIF", notes = "Deletes a jogador with a given NIF.")
    public ResponseEntity removerJogador(@PathVariable("nif") int nif) {
        try {
            jogadorHandler.removerJogador(nif);
            return ResponseEntity.ok().build();
        } catch (RemoverJogadorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update jogador", notes = "Updates the given jogador.")
    public ResponseEntity atualizarJogador(@RequestBody JogadorPostDto jogadorDto) {
        try {
            JogadorPostDto responseDto = jogadorHandler.atualizarJogador(jogadorDto);
            return ResponseEntity.ok(responseDto);
        } catch (AtualizarJogadorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @GetMapping
    @ApiOperation(value = "Get all jogadores", notes = "Returns all jogadores.")
    public ResponseEntity<Set<JogadorDto>> buscarJogadores() {
        Set<JogadorDto> jogadorDtos = jogadorHandler.buscarJogadores();
        return ResponseEntity.ok(jogadorDtos);
    }
}
