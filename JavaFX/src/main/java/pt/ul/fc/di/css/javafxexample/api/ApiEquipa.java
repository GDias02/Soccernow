package pt.ul.fc.di.css.javafxexample.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import pt.ul.fc.di.css.javafxexample.dto.equipas.EquipaDto;

public class ApiEquipa {
  private static final String BASE_URL = "http://localhost:8080/api/equipas";
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final HttpClient client = HttpClient.newHttpClient();
  private static final CollectionType listReference =
      TypeFactory.defaultInstance().constructCollectionType(List.class, EquipaDto.class);

  public static List<EquipaDto> verificarEquipas() throws Exception {
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

    return mapper.readValue(response.body(), listReference);
  }
}
