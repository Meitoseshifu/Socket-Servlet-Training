package happy.learning.servlet;

import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EveningServletHttpClient {
    @SneakyThrows
    public static void main(String[] args) {
        URI toEveningEndpoint = URI.create("http://192.168.0.189z:8080/evening?name=HttpClient");
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(toEveningEndpoint).build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());
    }
}
