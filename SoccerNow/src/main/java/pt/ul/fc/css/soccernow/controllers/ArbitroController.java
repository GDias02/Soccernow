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
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroPostDto;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.AtualizarArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.NotFoundException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RegistarArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.RemoverArbitroException;
import pt.ul.fc.css.soccernow.exceptions.utilizadores.VerificarArbitroException;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;

@RestController
@RequestMapping("/api/arbitros")
@Api(value = "Arbitro API", tags = "Arbitros")
public class ArbitroController {
    
    @Autowired
    private ArbitroHandler arbitroHandler;

    @PostMapping("/create")
    @ApiOperation(value = "Create arbitro", notes = "Creates a new arbitro and returns the created arbitro DTO.")
    public ResponseEntity registarArbitro(@RequestBody ArbitroPostDto arbitroDto) {
        try {
            ArbitroPostDto responseDto = arbitroHandler.registarArbitro(arbitroDto);
            return ResponseEntity.ok(responseDto);
        } catch (RegistarArbitroException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{nif}")
    @ApiOperation(value = "Get arbitro by NIF", notes = "Returns an arbitro given its NIF.")
    public ResponseEntity verificarArbitro(@PathVariable("nif") int nif) {
        try {
            ArbitroDto arbitroDto = arbitroHandler.verificarArbitro(nif);
            return ResponseEntity.ok(arbitroDto);
        } catch (VerificarArbitroException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @DeleteMapping("/{nif}")
    @ApiOperation(value = "Delete arbitro by NIF", notes = "Deletes an arbitro with a given NIF.")
    public ResponseEntity removerArbitro(@PathVariable("nif") int nif) {
        try {
            arbitroHandler.removerArbitro(nif);
            return ResponseEntity.ok().build();
        } catch (RemoverArbitroException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update arbitro", notes = "Updates the given arbitro.")
    public ResponseEntity atualizarArbitro(@RequestBody ArbitroPostDto arbitroDto) {
        try {
            ArbitroPostDto responseDto = arbitroHandler.atualizarArbitro(arbitroDto);
            return ResponseEntity.ok(responseDto);
        } catch (AtualizarArbitroException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @GetMapping
    @ApiOperation(value = "Get all arbitros", notes = "Returns all arbitros.")
    public ResponseEntity<Set<ArbitroDto>> buscarArbitros() {
        Set<ArbitroDto> arbitroDtos = arbitroHandler.buscarArbitros();
        return ResponseEntity.ok(arbitroDtos);
    }
}
