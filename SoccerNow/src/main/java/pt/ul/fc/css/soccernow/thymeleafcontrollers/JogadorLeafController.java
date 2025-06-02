package pt.ul.fc.css.soccernow.thymeleafcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.filtros.JogadorFiltros;
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
        model.addAttribute("filtros", new JogadorFiltros());
        return "jogador_lista";
    }

    @GetMapping("/jogador")
    @ApiOperation(value = "Get filtered jogadores", notes = "Returns jogadores that match the given filter.")
    public String filtrarJogadores(final Model model,
        @RequestParam(name="nome", required=false) String nome,
        @RequestParam(name="posicao", required=false) String posicao,
        @RequestParam(name="golos", required=false) String golos,
        @RequestParam(name="cartoes", required=false) String cartoes,
        @RequestParam(name="jogos", required=false) String jogos) {

        model.addAttribute("jogadores", jogadorHandler.filtrarJogadores(nome, posicao, golos, cartoes, jogos));
        model.addAttribute("filtros", new JogadorFiltros(nome, posicao, golos, cartoes, jogos));
        return "jogador_lista";
    }
}
