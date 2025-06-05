package pt.ul.fc.di.css.javafxexample.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import pt.ul.fc.di.css.javafxexample.dto.campeonatos.CampeonatoDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.JogoDto;
import pt.ul.fc.di.css.javafxexample.dto.jogos.SelecaoDto;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorDto;

public class ApiJogo {
  private static final String BASE_URL = "http://localhost:8080/api/jogos";
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final HttpClient client = HttpClient.newHttpClient();
  private static final CollectionType jogoListReference =
      TypeFactory.defaultInstance().constructCollectionType(List.class, JogoDto.class);
  private static final CollectionType jogadorListReference =
      TypeFactory.defaultInstance().constructCollectionType(List.class, JogadorDto.class);

  public static void createJogo(JogoDto jogo) throws Exception {
    mapper.registerModule(new JavaTimeModule());
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

  public static void finalizarJogo(JogoDto jogo) throws Exception {
    mapper.registerModule(new JavaTimeModule());
    String json = mapper.writeValueAsString(jogo);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/gameover"))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200 && response.statusCode() != 201) {
      throw new RuntimeException("Failed to create jogo. HTTP code: " + response.statusCode());
    }
  }

  public static List<JogoDto> getAllJogos() throws Exception {
    mapper.registerModule(new JavaTimeModule());
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL)).GET().build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200) {
      throw new RuntimeException("Failed to get jogos. HTTP code: " + response.statusCode());
    }

    return mapper.readValue(response.body(), jogoListReference);
  }

  public static List<JogoDto> getAllJogosPorTerminar() throws Exception {
    mapper.registerModule(new JavaTimeModule());
    HttpRequest request =
        HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/unfinished")).GET().build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200) {
      throw new RuntimeException("Failed to get jogos. HTTP code: " + response.statusCode());
    }

    return mapper.readValue(response.body(), jogoListReference);
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

  public static Boolean arbitrosDisponiveis(JogoDto jogo) throws Exception {
    mapper.registerModule(new JavaTimeModule());
    String json = mapper.writeValueAsString(jogo);
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/arbitros/available"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200 && response.statusCode() != 201) {
      throw new RuntimeException("Failed to get arbitros. HTTP code: " + response.statusCode());
    }

    System.out.println(response.body());
    return mapper.readValue(response.body(), Boolean.class);
  }

  public static Boolean jogadoresDisponiveis(JogoDto jogo) throws Exception {
    mapper.registerModule(new JavaTimeModule());
    String json = mapper.writeValueAsString(jogo);
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/jogadores/available"))
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

  public static List<JogadorDto> jogadoresDeSelecao(SelecaoDto selecao) throws Exception {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/selecao/" + selecao.getId() + "/jogadores"))
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200 && response.statusCode() != 201) {
      throw new RuntimeException(
          "Failed to get jogadores from Selecao. HTTP code: " + response.statusCode());
    }
    return mapper.readValue(response.body(), jogadorListReference);
  }
}
