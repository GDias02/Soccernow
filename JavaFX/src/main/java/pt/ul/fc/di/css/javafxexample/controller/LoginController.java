package pt.ul.fc.di.css.javafxexample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pt.ul.fc.di.css.javafxexample.api.ApiArbitro;
import pt.ul.fc.di.css.javafxexample.api.ApiJogador;

public class LoginController extends Controller {

    @FXML
    private TextField fieldNif;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextArea message;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        //Nothing here
    }

    @FXML
    void login(ActionEvent event) {

        message.clear();

        String nifStr = fieldNif.getText().trim();
        String password = fieldPassword.getText().trim();

        if (!validInputs(nifStr, password)) return;

        int nif = Integer.parseInt(nifStr);

        try {
            ApiJogador.verificarJogador(nif);
            switchToInit();
        } catch (Exception e1) {
            try {
                ApiArbitro.verificarArbitro(nif);
                switchToInit();
            } catch (Exception e2) {
                message.appendText("Credenciais Incorretas! Tente novamente.");
            }
        }
    }

    private void switchToInit() {
        Util.switchScene(getStage(),
            "/pt/ul/fc/di/css/javafxexample/view/init.fxml",
            "Init");
    }

    private boolean validInputs(String nif, String password) {
        boolean isValid = true;
        if (nif.isEmpty()) {
            message.appendText("O nif é obrigatório.\n");
            isValid = false;
        }
        if (nif.length() != 9 || !nif.matches("\\d+")) {
            message.appendText("O nif tem de ter 9 dígitos.\n");
            isValid = false;
        }
        if (password.isEmpty()) {
            message.appendText("A password é obrigatória.\n");
            isValid = false;
        }

        return isValid;
    }
}
