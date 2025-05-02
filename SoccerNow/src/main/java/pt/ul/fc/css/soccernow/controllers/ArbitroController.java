package pt.ul.fc.css.soccernow.controllers;

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
import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;

@RestController
@RequestMapping("/api/arbitros")
@Api(value = "Arbitro API", tags = "Arbitros")
public class ArbitroController {
    
    @Autowired
    private ArbitroHandler arbitroHandler;

    @PostMapping("/create")
    @ApiOperation(value = "Create arbitro", notes = "Creates a new arbitro and returns the created arbitro DTO.")
    public ResponseEntity<ArbitroDto> registarArbitro(@RequestBody ArbitroDto arbitroDto) {
        ArbitroDto responseDto = arbitroHandler.registarArbitro(arbitroDto);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{nif}")
    @ApiOperation(value = "Get arbitro by NIF", notes = "Returns an arbitro given its NIF.")
    public ResponseEntity<ArbitroDto> verificarArbitro(@PathVariable("nif") int nif) {
        ArbitroDto arbitroDto = arbitroHandler.verificarArbitro(nif);
        if (arbitroDto != null) {
            return ResponseEntity.ok(arbitroDto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{nif}")
    @ApiOperation(value = "Delete arbitro by NIF", notes = "Deletes an arbitro with a given NIF.")
    public ResponseEntity<ArbitroDto> removerArbitro(@PathVariable("nif") int nif) {
        arbitroHandler.removerArbitro(nif);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update arbitro", notes = "Updates the given arbitro.")
    public ResponseEntity<ArbitroDto> atualizarArbitro(@RequestBody ArbitroDto arbitroDto) {
        ArbitroDto responseDto = arbitroHandler.atualizarArbitro(arbitroDto);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.notFound().build();
    }
}
