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
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.ArbitroPostDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.CertificadoDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.UtilizadorDto;

public class DetalhesArbitroController extends Controller {

    private Long arbitro_id;

    private int arbitro_nif;

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
    private Button verificarButton;

    @FXML
    private Button removerButton;

    @FXML
    private Button atualizarButton;

    @FXML
    public void initialize() {
        removerButton.setDisable(true);
        atualizarButton.setDisable(true);
    }

    @FXML
    void verificar(ActionEvent event) {

        message.clear();

        String nifStr = fieldNif.getText().trim();

        if (!validInputsVerificar(nifStr)) return;

        int nif = Integer.parseInt(nifStr);

        try {
            ArbitroDto arbitro = ApiArbitro.verificarArbitro(nif);
            UtilizadorDto utilizador = arbitro.getUtilizador();
            CertificadoDto certificadoDto = arbitro.getCertificado();
            fieldNome.setText(utilizador.getNome());
            if (utilizador.getContacto() != null) {
                fieldTelefone.setText(utilizador.getContacto());
            }
            if (certificadoDto.isExistsCertificado())
                certificado.getToggles().get(0).setSelected(true);
            else
                certificado.getToggles().get(1).setSelected(true);

            arbitro_id = utilizador.getId();
            arbitro_nif = utilizador.getNif();
            removerButton.setDisable(false);
            atualizarButton.setDisable(false);

            message.appendText("Árbitro encontrado com sucesso!\n");

        } catch (Exception e) {
            message.appendText(e.getMessage() + ".\n");
        }
    }

    @FXML
    void remover(ActionEvent event) {

        message.clear();

        try {
            ApiArbitro.removerArbitro(arbitro_nif);

            fieldNif.clear();
            fieldNome.clear();
            fieldTelefone.clear();
            certificado.getSelectedToggle().setSelected(false);

            arbitro_id = 0L;
            arbitro_nif = 0;
            removerButton.setDisable(true);
            atualizarButton.setDisable(true);

            message.appendText("Árbitro removido com sucesso!\n");

        } catch (Exception e) {
            message.appendText(e.getMessage() + ".\n");
        }
    }

    @FXML
    void atualizar(ActionEvent event) {

        message.clear();

        String nifStr = fieldNif.getText().trim();
        String nome = fieldNome.getText().trim();
        String phone = fieldTelefone.getText().trim();
        RadioButton certificadoButton = (RadioButton) certificado.getSelectedToggle();
        CertificadoDto certificadoDto = certificadoButton.getText().equals("sem") ? new CertificadoDto(false) : new CertificadoDto(true);

        if (!validInputs(nifStr, nome)) return;

        int nif = Integer.parseInt(nifStr);

        UtilizadorDto utilizador = new UtilizadorDto(arbitro_id, nif, nome, phone);
        ArbitroPostDto dto = new ArbitroPostDto(utilizador, certificadoDto);

        try {
            ApiArbitro.atualizarArbitro(dto);
            arbitro_nif = nif;
            message.appendText("Árbitro atualizado com sucesso!\n");

        } catch (Exception e) {
            message.appendText(e.getMessage() + ".\n");
        }
    }

    private boolean validInputsVerificar(String nif) {
        boolean isValid = true;
        if (nif.isEmpty()) {
            message.appendText("O nif do árbitro é obrigatório.\n");
            isValid = false;
        }
        if (nif.length() != 9 || !nif.matches("\\d+")) {
            message.appendText("O nif do árbitro tem de ter 9 dígitos.\n");
            isValid = false;
        }

        return isValid;
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
