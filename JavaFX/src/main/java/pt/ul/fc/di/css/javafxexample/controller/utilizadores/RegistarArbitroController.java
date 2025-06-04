package pt.ul.fc.di.css.javafxexample.controller.utilizadores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import pt.ul.fc.di.css.javafxexample.api.ApiArbitro;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.ArbitroPostDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.CertificadoDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.UtilizadorDto;

public class RegistarArbitroController extends Controller {

    @FXML
    private TextField fieldNif;

    @FXML
    private TextField fieldNome;

    @FXML
    private TextField fieldTelefone;

    @FXML
    private ToggleGroup certificado;

    @FXML
    private TextArea message;

    @FXML
    private Button backButton;

    @FXML
    private Button registarButton;

    @FXML
    public void initialize() {
        //Nothing here
    }

    @FXML
    void registar(ActionEvent event) {

        message.clear();

        String nifStr = fieldNif.getText().trim();
        String nome = fieldNome.getText().trim();
        String phone = fieldTelefone.getText().trim();
        RadioButton certificadoButton = (RadioButton) certificado.getSelectedToggle();
        CertificadoDto certificadoDto = certificadoButton.getText().equals("sem") ? new CertificadoDto(false) : new CertificadoDto(true);

        if (!validInputs(nifStr, nome)) return;

        int nif = Integer.parseInt(nifStr);

        UtilizadorDto utilizador = new UtilizadorDto(0L, nif, nome, phone);
        ArbitroPostDto dto = new ArbitroPostDto(utilizador, certificadoDto);

        try {
            ApiArbitro.registarArbitro(dto);
            message.appendText("Árbitro registado com sucesso!\n");

        } catch (Exception e) {
            message.appendText(e.getMessage() + ".\n");
        }
    }

    private boolean validInputs(String nif, String nome) {
        boolean isValid = true;
        if (nif.isEmpty()) {
            message.appendText("O nif do árbitro é obrigatório.\n");
            isValid = false;
        }
        if (nif.length() != 9 || !nif.matches("\\d+")) {
            message.appendText("O nif do árbitro tem de ter 9 dígitos.\n");
            isValid = false;
        }
        if (nome.isEmpty()) {
            message.appendText("O nome do árbitro é obrigatório.\n");
            isValid = false;
        }

        return isValid;
    }

    @FXML
    void back(ActionEvent event) {
        Util.switchScene(getStage(),
            "/pt/ul/fc/di/css/javafxexample/view/utilizadores/init_utilizador.fxml",
            "Utilizadores");
    }
}
