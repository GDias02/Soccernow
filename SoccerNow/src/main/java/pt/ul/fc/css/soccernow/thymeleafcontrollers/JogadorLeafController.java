package pt.ul.fc.css.soccernow.thymeleafcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;

@Controller
@RequestMapping("/leaf/api/jogadores")
@Api(value = "Jogador API", tags = "Jogadores")
public class JogadorLeafController {
    
    @Autowired
    private JogadorHandler jogadorHandler;

    @GetMapping
    @ApiOperation(value = "Get all jogadores", notes = "Returns all jogadores.")
    public String buscarJogadores(final Model model) {
        model.addAttribute("jogadores", jogadorHandler.buscarJogadores());
        return "jogador_lista";
    }
}
