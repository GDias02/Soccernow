package pt.ul.fc.css.soccernow.thymeleafcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.ul.fc.css.soccernow.filtros.ArbitroFiltros;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;

@Controller
@RequestMapping("/leaf/api/arbitros")
@Api(value = "Arbitro API", tags = "Arbitros")
public class ArbitroLeafController {
    
    @Autowired
    private ArbitroHandler arbitroHandler;

    @GetMapping
    @ApiOperation(value = "Get all arbitros", notes = "Returns all arbitros.")
    public String buscarArbitros(final Model model) {
        model.addAttribute("arbitros", arbitroHandler.buscarArbitros());
        model.addAttribute("filtros", new ArbitroFiltros());
        return "arbitro_lista";
    }

    @GetMapping("/arbitro")
    @ApiOperation(value = "Get filtered arbitros", notes = "Returns arbitros that match the given filter.")
    public String filtrarArbitros(final Model model,
        @RequestParam(name="nome", required=false) String nome,
        @RequestParam(name="jogos", required=false) String jogos,
        @RequestParam(name="cartoes", required=false) String cartoes) {

        model.addAttribute("arbitros", arbitroHandler.filtrarArbitros(nome, jogos, cartoes));
        model.addAttribute("filtros", new ArbitroFiltros(nome, jogos, cartoes));
        return "arbitro_lista";
    }
}
