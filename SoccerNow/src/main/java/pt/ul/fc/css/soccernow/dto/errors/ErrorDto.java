package pt.ul.fc.css.soccernow.dto.errors;

import java.util.Map;
import java.util.HashMap;

/**
 * Data Transfer Object (DTO) for representing error information.
 */
public class ErrorDto {
    private String mensagem;
    private Map<String, String> detalhes;

    /**
     * Default constructor that initializes an empty error message and details.
     */
    public ErrorDto(){
        this.mensagem = "";
        this.detalhes = new HashMap<>();
    }

    /**
     * Constructor that initializes the error message and details.
     *
     * @param mensagem The error message.
     * @param detalhes A map containing additional details about the error.
     */
    public ErrorDto(String mensagem, Map<String, String> detalhes) {
        this.mensagem = mensagem;
        this.detalhes = detalhes;
    }

    /**
     * Gets the error message.
     *
     * @return The error message.
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Sets the error message.
     *
     * @param mensagem The error message to set.
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Gets the details of the error.
     *
     * @return A map containing additional details about the error.
     */
    public Map<String, String> getDetalhes() {
        return detalhes;
    }

    /**
     * Sets the details of the error.
     *
     * @param detalhes A map containing additional details about the error to set.
     */
    public void setDetalhes(Map<String, String> detalhes) {
        this.detalhes = detalhes;
    }
}

