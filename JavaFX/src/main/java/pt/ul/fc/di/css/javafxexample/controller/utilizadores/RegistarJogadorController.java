package pt.ul.fc.di.css.javafxexample.controller.utilizadores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import pt.ul.fc.di.css.javafxexample.api.ApiJogador;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.UtilizadorDto;

public class RegistarJogadorController extends Controller {

    private String originalNif;

    @FXML
    private Button btnAdd;

    @FXML
    private Menu itemBack;

    @FXML
    private TextField fieldPhone;

    @FXML
    private TextField fieldNIF;

    @FXML
    private TextField fieldNome;

    @FXML
    private Label labelAddSuccess;

    @FXML
    private Label labelStatus;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelPhone;

    @FXML
    private Label labelNIF;

    @FXML
    void registarJogador(ActionEvent event) {

        String nome = fieldNome.getText().trim();
        String phone = fieldPhone.getText().trim();
        String nifStr = fieldNIF.getText().trim();

        if (!validInputs(nome, phone, nifStr)) {
            labelAddSuccess.setVisible(false);
            labelStatus.setText("✘");
            return;
        }

        int nif = Integer.parseInt(nifStr);

        JogadorDto currentJogador = null; //ApiJogador.getJogador(nif);
        UtilizadorDto utilizador = new UtilizadorDto(0L, nif, nome, phone);
        JogadorPostDto dto = new JogadorPostDto(utilizador, null);

        try {
            if (currentJogador != null && nifStr.equals(originalNif)) {
                // ApiJogador.updateJogador(dto); 
                labelStatus.setText("Jogador atualizado ✔");
            } else {
                ApiJogador.createCustomer(dto); //!!!!!!!!!!!!
                labelStatus.setText("Novo jogador adicionado ✔");
            }

            unbindFields();

            labelNome.textProperty().bindBidirectional(fieldNome.textProperty());
            labelPhone.textProperty().bindBidirectional(fieldPhone.textProperty());
            labelNIF.textProperty().bindBidirectional(fieldNIF.textProperty());

            labelAddSuccess.setVisible(true);
            labelNome.setVisible(true);
            labelPhone.setVisible(true);
            labelNIF.setVisible(true);
            labelStatus.setVisible(true);

            originalNif = nifStr;
            fieldNIF.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.equals(originalNif)) {
                    labelStatus.setText("Atualizando jogador 🔄");
                } else {
                    labelStatus.setText("Adicionando jogador 🆕");
                }
            });

        } catch (Exception e) {
            unbindFields();

            labelNome.setText("Error");
            labelPhone.setText("Error");
            labelNIF.setText("Error");
            labelStatus.setText("✘");

            labelNome.setVisible(true);
            labelPhone.setVisible(true);
            labelNIF.setVisible(true);
            labelStatus.setVisible(true);

            labelAddSuccess.setVisible(false);
            e.printStackTrace();
        }
    }

    private void unbindFields() {
        labelNIF.textProperty().unbindBidirectional(fieldNIF.textProperty());
        labelPhone.textProperty().unbindBidirectional(fieldPhone.textProperty());
        labelNome.textProperty().unbindBidirectional(fieldNome.textProperty());
    }

    private boolean validInputs(String nome, String phone, String nif) {
        boolean isValid = true;
        if (nome.isEmpty()) {
            labelNome.textProperty().unbindBidirectional(fieldNome.textProperty());

            labelNome.setText("Name required");
            labelNome.setVisible(true);
            isValid = false;
        }
        if (phone.isEmpty()) {
            labelPhone.textProperty().unbindBidirectional(fieldPhone.textProperty());
            labelPhone.setText("Phone required");
            labelPhone.setVisible(true);
            isValid = false;
        }
        if (nif.isEmpty()) {
            labelNIF.textProperty().unbindBidirectional(fieldNIF.textProperty());
            labelNIF.setText("NIF required");
            labelNIF.setVisible(true);
            isValid = false;
        }
        if (nif.length() != 9) {
            labelNIF.textProperty().unbindBidirectional(fieldNIF.textProperty());
            labelNIF.setText("NIF must be 9 digits");
            labelNIF.setVisible(true);
            isValid = false;
        }

        return isValid;
    }

    @FXML
    public void initialize() {
        labelAddSuccess.setVisible(false);
        labelNome.setVisible(false);
        labelPhone.setVisible(false);
        labelNIF.setVisible(false);
        labelStatus.setVisible(false);
    }

    @FXML
    void back(ActionEvent event) {
        Util.switchScene(getStage(), "/pt/ul/fc/di/css/javafxexample/view/init.fxml", "SoccerNow");
    }
}
