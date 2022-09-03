package services;

import com.google.gson.Gson;
import dto.AddressResponseDto;
import model.Address;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class HttpServices {

    private static String url = "https://viacep.com.br/ws/%s/json";
    private static HttpClient httpClient;
    private static HttpRequest httpRequest;
    
    public HttpServices(String clientZipCode) throws IOException, InterruptedException {
        httpClient = HttpClient.newHttpClient();
        httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format(url, clientZipCode)))
                .header("accept", "application/json")
                .GET()
                .build();
    }

    public AddressResponseDto getAddress() throws IOException, InterruptedException {
        var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), AddressResponseDto.class);
    }

}