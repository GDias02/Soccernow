package pt.ul.fc.di.css.javafxexample.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.di.css.javafxexample.dto.utilizadores.JogadorPostDto;

public class ApiJogador {

    private static final String BASE_URL = "http://localhost:8080";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void createCustomer(JogadorPostDto jogador) throws Exception {
        String json = mapper.writeValueAsString(jogador);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/customers"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Failed to create customer. HTTP code: " + response.statusCode());
        }
    }

    /* public static List<CustomerDto> getAllCustomers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/customers"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get customers. HTTP code: " + response.statusCode());
        }

        return mapper.readValue(response.body(), new TypeReference<List<CustomerDto>>() {
        });
    } */

    /* public static List<SaleProductDto> getProductByVat(String vat) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/sales/products/" + vat))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get sales. HTTP code: " + response.statusCode());
        }

        return mapper.readValue(response.body(), new TypeReference<List<SaleProductDto>>() {
        });
    } */

    /* public static void createSale(String vat) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/sales/create"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(vat))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201 && response.statusCode() != 200) {
            throw new RuntimeException("Failed to create sale. HTTP code: " + response.statusCode());
        }
    } */
}
