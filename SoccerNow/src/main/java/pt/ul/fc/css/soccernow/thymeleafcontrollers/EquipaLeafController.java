package pt.ul.fc.css.soccernow.thymeleafcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.dto.equipas.ConquistaDto;
import pt.ul.fc.css.soccernow.dto.equipas.EquipaDto;
import pt.ul.fc.css.soccernow.handlers.ConquistaHandler;
import pt.ul.fc.css.soccernow.handlers.EquipaHandler;

@Controller
@RequestMapping("/leaf/api/equipas")
@Api(value = "Equipa API", tags = "Equipas")
public class EquipaLeafController {
    
    @Autowired
    private EquipaHandler equipaHandler;

    @Autowired
    private ConquistaHandler conquistaHandler;

    /** IN REFERENCE TO EQUIPA */

    @GetMapping
    @ApiOperation(value="Get all equipas", notes="Returns a list of all equipas.")
    public ResponseEntity<List<EquipaDto>> verificarEquipas(){
        List<EquipaDto> equipas = equipaHandler.verificarEquipas();
        if (equipas == null || equipas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(equipas);
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
}
