package pt.ul.fc.di.css.javafxexample.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.di.css.javafxexample.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.JogoDto;

public class ApiCampeonato {

    private static final String BASE_URL = "http://localhost:8080/api/campeonatos";
    private static final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    private static final HttpClient client = HttpClient.newHttpClient();


    public static List<CampeonatoDto> buscarCampeonatos() throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch Campeonatos. HTTP code: " + response.statusCode());
        }

        // Process the response body as needed
        String responseBody = response.body();
        List<CampeonatoDto> Campeonatos = mapper.readValue(responseBody, mapper.getTypeFactory().constructCollectionType(List.class, CampeonatoDto.class));
        return Campeonatos;
    }

    public static void registarCampeonato(CampeonatoDto campeonatoDto) throws Exception {
        String json = mapper.writeValueAsString(campeonatoDto);
        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Failed to create Campeonatos. HTTP code: " + response.statusCode());
        }        
    }

    public static List<CampeonatoDto> getCampeonatosAlteraveis() throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/search?search=estado:AGENDADO"))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new RuntimeException("Failed to fetch Campeonatos. HTTP code: " + response.statusCode());
        }

        // Process the response body as needed
        String responseBody = response.body();
        return mapper.readValue(responseBody, mapper.getTypeFactory().constructCollectionType(List.class, CampeonatoDto.class));
    }

    public static List<JogoDto> getJogosDeCampeonato(Long idCampeonato) throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/" + idCampeonato + "/jogos"))
                        .header("Content-Type", "application/json")
                        .GET()
                        .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new RuntimeException("Failed to fetch Campeonatos. HTTP code: " + response.statusCode());
        }

        // Process the response body as needed
        String responseBody = response.body();
        return mapper.readValue(responseBody, mapper.getTypeFactory().constructCollectionType(List.class, JogoDto.class));
    }
}
