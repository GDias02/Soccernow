package pt.ul.fc.di.css.javafxexample.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import pt.ul.fc.di.css.javafxexample.dto.utilizadores.ArbitroDto;

public class ApiArbitro {
  private static final String BASE_URL = "http://localhost:8080/api/arbitros";
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final HttpClient client = HttpClient.newHttpClient();

  public static Set<ArbitroDto> buscarArbitros() throws Exception {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL))
            .header("Content-Type", "application/json")
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200) {
      throw new RuntimeException(response.body());
    }

    return mapper.readValue(response.body(), new TypeReference<Set<ArbitroDto>>() {});
  }
}
