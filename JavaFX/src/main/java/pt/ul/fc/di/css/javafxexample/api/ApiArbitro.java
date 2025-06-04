package pt.ul.fc.di.css.javafxexample.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.di.css.javafxexample.dto.utilizadores.ArbitroDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.ArbitroPostDto;

public class ApiArbitro {

    private static final String BASE_URL = "http://localhost:8080/api/arbitros";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    public static ArbitroDto registarArbitro(ArbitroPostDto arbitro) throws Exception {
        String json = mapper.writeValueAsString(arbitro);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/create"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(response.body());
        }

        return mapper.readValue(response.body(), new TypeReference<ArbitroDto>() {
        });
    }

    public static ArbitroDto verificarArbitro(int nif) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + nif))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(response.body());
        }

        return mapper.readValue(response.body(), new TypeReference<ArbitroDto>() {
        });
    }

    public static void removerArbitro(int nif) throws Exception {
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

    public static ArbitroPostDto atualizarArbitro(ArbitroPostDto arbitro) throws Exception {
        String json = mapper.writeValueAsString(arbitro);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/update"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(response.body());
        }

        return mapper.readValue(response.body(), new TypeReference<ArbitroPostDto>() {
        });
    }

    public static Set<ArbitroDto> buscarArbitros() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(response.body());
        }

        return mapper.readValue(response.body(), new TypeReference<Set<ArbitroDto>>() {
        });
    }
}
