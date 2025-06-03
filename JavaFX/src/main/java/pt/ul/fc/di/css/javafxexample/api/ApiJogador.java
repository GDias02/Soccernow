package pt.ul.fc.di.css.javafxexample.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorPostDto;

public class ApiJogador {

    private static final String BASE_URL = "http://localhost:8080/api/jogadores";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    public static JogadorPostDto registarJogador(JogadorPostDto jogador) throws Exception {
        String json = mapper.writeValueAsString(jogador);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/create"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(response.body());
        }

        return mapper.readValue(response.body(), new TypeReference<JogadorPostDto>() {
        });
    }

    public static JogadorDto verificarJogador(int nif) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + nif))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(response.body());
        }

        return mapper.readValue(response.body(), new TypeReference<JogadorDto>() {
        });
    }

    public static void removerJogador(int nif) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + nif))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(response.body());
        }
    }

    public static JogadorPostDto verificarJogador(JogadorPostDto jogador) throws Exception {
        String json = mapper.writeValueAsString(jogador);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/update"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(response.body());
        }

        return mapper.readValue(response.body(), new TypeReference<JogadorPostDto>() {
        });
    }
}
