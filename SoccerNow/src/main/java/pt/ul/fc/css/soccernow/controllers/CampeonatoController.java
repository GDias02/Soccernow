package pt.ul.fc.css.soccernow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.handlers.CampeonatoHandler;

@RestController
@RequestMapping("/api/campeonatos")
@Api(value = "Campeonato API", tags = "Campeonatos")
public class CampeonatoController {
    
    @Autowired
    private CampeonatoHandler campeonatoHandler;

    @GetMapping("{id}")
    @ApiOperation(value = "Gets campeonato by id", note= "Gets the equipa with given id")
    public ResponseEntity<CampeonatoDto> verificarCampeonato(@PathVariable("id") Long id){
        
    }
}
