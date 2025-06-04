package pt.ul.fc.css.soccernow.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.soccernow.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.handlers.CampeonatoHandler;

@RestController
@RequestMapping("/api/campeonatos")
@Api(value = "Campeonato API", tags = "Campeonatos")
public class CampeonatoController {

  @Autowired private CampeonatoHandler campeonatoHandler;

  @GetMapping
  @ApiOperation(value = "Gets all campeonatos", notes = "Returns a list of all campeonatos")
  public ResponseEntity<List<CampeonatoDto>> verificarCampeonatos() {
    List<CampeonatoDto> campeonatos = campeonatoHandler.verificarCampeonatos();
    return ResponseEntity.ok(campeonatos);
  }

  @GetMapping("{id}")
  @ApiOperation(value = "Gets campeonato by id", notes = "Gets the equipa with given id")
  public ResponseEntity<CampeonatoDto> verificarCampeonato(@PathVariable("id") Long id) {
    CampeonatoDto campeonato = campeonatoHandler.verificarCampeonato(id);
    if (campeonato == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(campeonato);
  }

  @PostMapping
  @ApiOperation(
      value = "Creates a new campeonato",
      notes = "Creates a new campeonato with the provided details")
  public ResponseEntity<CampeonatoDto> criarCampeonato(@RequestBody CampeonatoDto campeonatoDto) {
    CampeonatoDto campeonato = campeonatoHandler.criarCampeonato(campeonatoDto);
    if (campeonato == null) return ResponseEntity.badRequest().build();
    return ResponseEntity.status(HttpStatus.CREATED).body(campeonato);
  }

  @PutMapping("{id}")
  @ApiOperation(
      value = "Updates an existing campeonato",
      notes = "Updates the details of an existing campeonato")
  public ResponseEntity<CampeonatoDto> atualizarCampeonato(
      @PathVariable("id") Long id, @RequestBody CampeonatoDto campeonatoDto) {
    CampeonatoDto campeonato = campeonatoHandler.atualizarCampeonato(id, campeonatoDto);
    if (campeonato == null) return ResponseEntity.notFound().build();
    if (campeonato.getId() == -1) return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    return ResponseEntity.ok(campeonato);
  }

  @DeleteMapping("{id}")
  @ApiOperation(value = "Deletes campeonato by id", notes = "Deletes campeonato with given id")
  public ResponseEntity<CampeonatoDto> removerCampeonato(@PathVariable("id") Long id) {
    CampeonatoDto campeonato = campeonatoHandler.removerCampeonato(id);
    if (campeonato == null) return ResponseEntity.notFound().build();
    if (campeonato.getId() == -1) return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    return ResponseEntity.ok(campeonato);
  }
/*
  @GetMapping("/search")
  @ApiOperation(
    value = "Get campeonatos by filter",
    notes = "Gets a list of Campeonatos that respect the given filters")
  public ResponseEntity<List<CampeonatoDto>> search(@RequestParam(value = "search") String search){
    List<CampeonatoDto> campeonatos = campeonatoHandler.search(search);
    if (search == null || campeonatos.isEmpty()) return ResponseEntity.noContent().build();
    return ResponseEntity.ok(campeonatos);
  } */
}
