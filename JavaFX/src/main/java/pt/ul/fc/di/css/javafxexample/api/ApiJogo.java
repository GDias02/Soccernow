package pt.ul.fc.di.css.javafxexample.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import pt.ul.fc.di.css.javafxexample.dto.jogos.JogoDto;

public class ApiJogo {
  private static final String BASE_URL = "http://localhost:8080/api/jogos";
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final HttpClient client = HttpClient.newHttpClient();
  private static final MapLikeType mapLikeType =
      mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, JogoDto.class);
  private static final CollectionType listReference =
      TypeFactory.defaultInstance().constructCollectionType(List.class, JogoDto.class);

  public static void createJogo(JogoDto jogo) throws Exception {
    String json = mapper.writeValueAsString(jogo);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/create"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200 && response.statusCode() != 201) {
      throw new RuntimeException("Failed to create jogo. HTTP code: " + response.statusCode());
    }
  }

  public static List<JogoDto> getAllJogos() throws Exception {

    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL)).GET().build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200) {
      throw new RuntimeException("Failed to get jogos. HTTP code: " + response.statusCode());
    }

    return mapper.readValue(response.body(), listReference);
  }

  public static Boolean localDisponivel(JogoDto jogo) throws Exception {
    mapper.registerModule(new JavaTimeModule());
    String json = mapper.writeValueAsString(jogo);
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/local/available"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200 && response.statusCode() != 201) {
      throw new RuntimeException("Failed to get jogos. HTTP code: " + response.statusCode());
    }

    System.out.println(response.body());
    return mapper.readValue(response.body(), Boolean.class);
  }
}
