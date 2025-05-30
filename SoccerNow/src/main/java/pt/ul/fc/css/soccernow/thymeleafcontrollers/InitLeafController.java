package pt.ul.fc.css.soccernow.thymeleafcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.handlers.InitHandler;

@Controller
@RequestMapping("/leaf/api/init")
@Api(value = "Init API", tags = "Init")
public class InitLeafController {
    
    @Autowired
    private InitHandler initHandler;

    @GetMapping
    @ApiOperation(value = "Populate DB", notes = "Repopulates the database with example rows.")
    public ResponseEntity<Void> init() {
        initHandler.init();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @ApiOperation(value = "Murder DB", notes = "Goes on a killing spree for the database.")
    public ResponseEntity<Void> assassinate() {
        initHandler.assassinate();
        return ResponseEntity.ok().build();
    }
}
