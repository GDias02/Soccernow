package pt.ul.fc.di.css.javafxexample.controller.utilizadores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pt.ul.fc.di.css.javafxexample.api.ApiJogador;
import pt.ul.fc.di.css.javafxexample.controller.Controller;
import pt.ul.fc.di.css.javafxexample.controller.Util;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorPostDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.Posicao;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.UtilizadorDto;

public class DetalhesJogadorController extends Controller {

    private Long jogador_id;

    private int jogador_nif;

    @FXML
    private TextField fieldNif;

    @FXML
    private TextField fieldNome;

    @FXML
    private TextField fieldTelefone;

    @FXML
    private ChoiceBox<String> fieldPosicao;

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
        fieldPosicao.getItems().removeAll(fieldPosicao.getItems());
        fieldPosicao.getItems().addAll("GUARDA REDES", "FIXO", "ALA DIREITA", "ALA ESQUERDA", "PIVO");

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
            JogadorDto jogador = ApiJogador.verificarJogador(nif);
            UtilizadorDto utilizador = jogador.getUtilizador();
            fieldNome.setText(utilizador.getNome());
            if (utilizador.getContacto() != null) {
                fieldTelefone.setText(utilizador.getContacto());
            }
            fieldPosicao.getSelectionModel().select(getPosicaoOption(jogador.getPosicaoPreferida()));

            jogador_id = utilizador.getId();
            jogador_nif = utilizador.getNif();
            removerButton.setDisable(false);
            atualizarButton.setDisable(false);

            message.appendText("Jogador encontrado com sucesso!\n");

        } catch (Exception e) {
            message.appendText(e.getMessage() + ".\n");
        }
    }

    @FXML
    void remover(ActionEvent event) {

        message.clear();

        try {
            ApiJogador.removerJogador(jogador_nif);

            fieldNif.clear();
            fieldNome.clear();
            fieldTelefone.clear();
            fieldPosicao.getSelectionModel().clearSelection();

            jogador_id = 0L;
            jogador_nif = 0;
            removerButton.setDisable(true);
            atualizarButton.setDisable(true);

            message.appendText("Jogador removido com sucesso!\n");

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
        String posicaoStr = (String) fieldPosicao.getSelectionModel().getSelectedItem();

        if (!validInputs(nifStr, nome, posicaoStr)) return;

        int nif = Integer.parseInt(nifStr);
        Posicao posicao = getPosicao(posicaoStr);

        UtilizadorDto utilizador = new UtilizadorDto(jogador_id, nif, nome, phone);
        JogadorPostDto dto = new JogadorPostDto(utilizador, posicao);

        try {
            ApiJogador.atualizarJogador(dto);
            jogador_nif = nif;
            message.appendText("Jogador atualizado com sucesso!\n");

        } catch (Exception e) {
            message.appendText(e.getMessage() + ".\n");
        }
    }

    private boolean validInputsVerificar(String nif) {
        boolean isValid = true;
        if (nif.isEmpty()) {
            message.appendText("O nif do jogador é obrigatório.\n");
            isValid = false;
        }
        if (nif.length() != 9 || !nif.matches("\\d+")) {
            message.appendText("O nif do jogador tem de ter 9 dígitos.\n");
            isValid = false;
        }

        return isValid;
    }

    private boolean validInputs(String nif, String nome, String posicao) {
        boolean isValid = true;
        if (nif.isEmpty()) {
            message.appendText("O nif do jogador é obrigatório.\n");
            isValid = false;
        }
        if (nif.length() != 9 || !nif.matches("\\d+")) {
            message.appendText("O nif do jogador tem de ter 9 dígitos.\n");
            isValid = false;
        }
        if (nome.isEmpty()) {
            message.appendText("O nome do jogador é obrigatório.\n");
            isValid = false;
        }
        if (!fieldPosicao.getItems().contains(posicao)) {
            message.appendText("A posição preferida do jogador é obrigatória.\n");
            isValid = false;
        }

        return isValid;
    }

    private Posicao getPosicao(String posicao) {
        switch (posicao) {
            case "GUARDA REDES" -> {return Posicao.GUARDA_REDES;}
            case "FIXO" -> {return Posicao.FIXO;}
            case "ALA DIREITA" -> {return Posicao.ALA_DIREITA;}
            case "ALA ESQUERDA" -> {return Posicao.ALA_ESQUERDA;}
            case "PIVO" -> {return Posicao.PIVO;}
        }
        return null;
    }

    private String getPosicaoOption(Posicao posicao) {
        switch (posicao) {
            case Posicao.GUARDA_REDES -> {return "GUARDA REDES";}
            case Posicao.FIXO -> {return "FIXO";}
            case Posicao.ALA_DIREITA -> {return "ALA DIREITA";}
            case Posicao.ALA_ESQUERDA -> {return "ALA ESQUERDA";}
            case Posicao.PIVO -> {return "PIVO";}
        }
        return null;
    }

    @FXML
    void back(ActionEvent event) {
        Util.switchScene(getStage(),
            "/pt/ul/fc/di/css/javafxexample/view/utilizadores/init_utilizador.fxml",
            "Utilizadores");
    }
}
