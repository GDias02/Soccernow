package pt.ul.fc.css.soccernow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.handlers.InitHandler;

@RestController
@RequestMapping("/api/init")
@Api(value = "Init API", tags = "Init")
public class InitController {
    
    @Autowired
    private InitHandler initHandler;

    @GetMapping
    @ApiOperation(value = "Populate DB", notes = "Repopulates the database with example rows.")
    public ResponseEntity<Void> init() {
        initHandler.init();
        return ResponseEntity.ok().build();
    }
}
