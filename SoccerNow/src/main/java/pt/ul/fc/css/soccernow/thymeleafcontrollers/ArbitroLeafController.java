package pt.ul.fc.css.soccernow.thymeleafcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;

@Controller
@RequestMapping("/leaf/api/arbitros")
@Api(value = "Arbitro API", tags = "Arbitros")
public class ArbitroLeafController {
    
    @Autowired
    private ArbitroHandler arbitroHandler;
}
