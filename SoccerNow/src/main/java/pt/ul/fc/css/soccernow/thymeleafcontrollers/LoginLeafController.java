package pt.ul.fc.css.soccernow.thymeleafcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.ul.fc.css.soccernow.dto.utilizadores.ArbitroDto;
import pt.ul.fc.css.soccernow.dto.utilizadores.JogadorDto;
import pt.ul.fc.css.soccernow.filtros.Auth;
import pt.ul.fc.css.soccernow.handlers.ArbitroHandler;
import pt.ul.fc.css.soccernow.handlers.JogadorHandler;

@Controller
@RequestMapping("")
public class LoginLeafController {

    @Autowired private JogadorHandler jogadorHandler;

    @Autowired private ArbitroHandler ArbitroHandler;

    @GetMapping
    public String launch(final Model model) {
        model.addAttribute("auth", new Auth());
        model.addAttribute("erro", "");
        return "login";
    }

    @GetMapping("/leaf")
    public String login(final Model model,
        @RequestParam(name="nif", required=true) String nif,
        @RequestParam(name="password", required=true) String password) {

        try {
            validInputs(nif, password);
            int nifInt = Integer.parseInt(nif);
            authenticate(nifInt, password);
            return "main-page";
        } catch (Exception e) {
            model.addAttribute("auth", new Auth());
            model.addAttribute("erro", e.getMessage());
            return "login";
        }
    }

    private void authenticate(int nif, String password) throws Exception {
        try {
            JogadorDto jogador = jogadorHandler.verificarJogador(nif);
            //check password
        } catch (Exception e1) {
            try {
                ArbitroDto arbitro = ArbitroHandler.verificarArbitro(nif);
                //check password
            } catch (Exception e2) {
                throw new Exception("Credenciais Incorretas!");
            }
        }
    }

    private void validInputs(String nif, String password) throws Exception {
        if (nif.isEmpty()) {
            throw new Exception("O nif é obrigatório.\n");
        }
        if (nif.length() != 9) {
            throw new Exception("O nif tem de ter 9 dígitos.\n");
        }
        if (password.isEmpty()) {
            throw new Exception("A password é obrigatória.\n");
        }
    }
}
