package pt.ul.fc.css.soccernow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;

import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.dto.equipas.ConquistaDto;
import pt.ul.fc.css.soccernow.handlers.ConquistaHandler;
import pt.ul.fc.css.soccernow.handlers.EquipaHandler;

import java.util.List;

@RestController
@RequestMapping("/api/equipas")
@Api(value = "Equipa API", tags = "Equipas")
public class EquipaController {
    
    @Autowired
    private EquipaHandler equipaHandler;

    @Autowired
    private ConquistaHandler conquistaHandler;

    /** IN REFERENCE TO EQUIPA */

    @GetMapping("{id}")
    @ApiOperation(value="Get equipa by id",notes="Returns Equipa with given id")
    public ResponseEntity<EquipaDto> verificarEquipa(@PathVariable("id") Long id){
        EquipaDto equipa = equipaHandler.verificarEquipa(id);
        if (equipa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipa);
    }
    
    @DeleteMapping("{id}")
    @ApiOperation(value="Remove equipa by id", notes="Deletes an Equipa with the given ID, returns the Equipa that was deleted")
    public ResponseEntity<EquipaDto> removerEquipa(@PathVariable("id") Long id){
        EquipaDto equipa = equipaHandler.removerEquipa(id);
        if (equipa == null) {
            return ResponseEntity.notFound().build();
        } else if (equipa.getId() == -1){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(null); 
        }
        return ResponseEntity.ok(equipa);
    }

    @PutMapping("{id}")
    @ApiOperation(value="Update an existing equipa", notes="Updates the details of an existing equipa identified by its ID. The updated equipa data should be provided in the request body.")
    public ResponseEntity<EquipaDto> atualizarEquipa(@PathVariable("id") Long id, @RequestBody EquipaDto equipaDto) {
        EquipaDto equipa = equipaHandler.atualizarEquipa(id, equipaDto);
        if (equipa == null) {
            return ResponseEntity.notFound().build();
        } else if (equipa.getId() == -1){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(null);
        }
        return ResponseEntity.ok(equipa);
    }

    @GetMapping
    @ApiOperation(value="Get all equipas", notes="Returns a list of all equipas.")
    public ResponseEntity<List<EquipaDto>> verificarEquipas(){
        List<EquipaDto> equipas = equipaHandler.verificarEquipas();
        if (equipas == null || equipas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(equipas);
    }

    @PostMapping
    @ApiOperation(value="Post an equipa", notes="Creates a new equipa and returns the equipa that was created")
    public ResponseEntity<EquipaDto> registarEquipa(@RequestBody EquipaDto equipaDto){
        EquipaDto equipa = equipaHandler.registarEquipa(equipaDto);
        if (equipa == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(equipa);
    }

    /** IN REFERENCE TO CONQUISTA */ 

    @GetMapping("{id}/conquistas")
    @ApiOperation(value="Get all Conquistas of a given equipa", notes="Returns all Conquistas of an Equipa")
    public ResponseEntity<List<ConquistaDto>> verificarConquistas(@PathVariable("id") Long id){
        List<ConquistaDto> conquistas = conquistaHandler.verificarConquistas(id);
        if (conquistas == null || conquistas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(conquistas);
    }

    @GetMapping("{id}/conquistas/{conquistaId}")
    @ApiOperation(value="Get Conquista by id", notes="Returns Conquista with given id of a given Equipa")
    public ResponseEntity<ConquistaDto> verificarConquista(@PathVariable("id") Long id, @PathVariable("conquistaId") Long conquistaId){
        ConquistaDto conquista = conquistaHandler.verificarConquista(id, conquistaId);
        if (conquista == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conquista);
    }

    @PostMapping("{id}/conquistas")
    @ApiOperation(value="Post a conquista to an Equipa", notes="Creates a new Conquista for the given Equipa and returns the created Conquista")
    public ResponseEntity<ConquistaDto> registarConquista(@PathVariable("id") Long id, @RequestBody ConquistaDto conquistaDto){
        ConquistaDto conquista = conquistaHandler.registarConquista(id, conquistaDto);
        if (conquista == null) {
            return ResponseEntity.badRequest().build();
        } else if (conquista.getId() == -1){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(null);
        }
        return ResponseEntity.ok(conquista);
    }

    @DeleteMapping("{id}/conquistas/{conquistaId}")
    @ApiOperation(value="Remove a conquista from an Equipa", notes="Deletes a Conquista with the given ID from the Equipa") 
    public ResponseEntity<ConquistaDto> removerConquista(@PathVariable("id") Long id, @PathVariable("conquistaId") Long conquistaId){
        ConquistaDto conquista = conquistaHandler.removerConquista(id, conquistaId);
        if (conquista == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conquista);
    }

    @PutMapping("{id}/conquistas/{conquistaId}")
    @ApiOperation(value="Update a conquista of an Equipa", notes="Updates the details of an existing Conquista for the given Equipa. The updated Conquista data should be provided in the request body.")
    public ResponseEntity<ConquistaDto> atualizarConquista(@PathVariable("id") Long id, @PathVariable("conquistaId") long conquistaId, @RequestBody ConquistaDto conquistaDto){
        ConquistaDto conquista = conquistaHandler.atualizarConquista(id, conquistaId, conquistaDto);
        if (conquista == null) {
            return ResponseEntity.notFound().build();
        } else if (conquista.getId() == -1){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(null);
        }
        return ResponseEntity.ok(conquista);
    }

}
