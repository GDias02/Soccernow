package pt.ul.fc.di.css.javafxexample.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.di.css.javafxexample.dto.equipas.EquipaDto;

public class ApiEquipa {

    private static final String BASE_URL = "http://localhost:8080";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void createEquipa(EquipaDto equipa) throws Exception {
        String json = mapper.writeValueAsString(equipa);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/equipas"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201){
            throw new RuntimeException("Failed to create equipa. HTTP code: " + response.statusCode());
        }
    }

    public static List<EquipaDto> buscarEquipas() throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/equipas"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch equipas. HTTP code: " + response.statusCode());
        }

        // Process the response body as needed
        String responseBody = response.body();
        List<EquipaDto> equipas = mapper.readValue(responseBody, mapper.getTypeFactory().constructCollectionType(List.class, EquipaDto.class));
        return equipas;
    }

    public static EquipaDto buscarEquipaPorId(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/equipas/" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch equipa. HTTP code: " + response.statusCode());
        }

        String responseBody = response.body();
        return mapper.readValue(responseBody, EquipaDto.class);
    }

    public static EquipaDto atualizarEquipa(EquipaDto equipaDto) throws Exception{
        String json = mapper.writeValueAsString(equipaDto);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/api/equipas/" + equipaDto.getId().toString()))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new RuntimeException("Failed to fetch equipa. HTTP code: " + response.statusCode());
        }

        String responseBody = response.body();
        return mapper.readValue(responseBody, EquipaDto.class);
    }

    public static EquipaDto removerEquipa(Long id) throws Exception{

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/api/equipas/" + id.toString()))
            .header("Content-Type", "application/json")
            .DELETE()
            .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204 && response.statusCode() != 202) {
            throw new RuntimeException("Failed to fetch equipa. HTTP code: " + response.statusCode());
        }

        String responseBody = response.body();
        return mapper.readValue(responseBody, EquipaDto.class);
    }
}
