package services;

import model.Address;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class HttpServices {

    private static String url = "https://viacep.com.br/ws/%s/json";
    private HttpClient httpClient;
    private HttpRequest httpRequest;
    
    public HttpServices(String clientZipCode) {
        this.httpClient = HttpClient.newHttpClient();
        this.httpRequest = HttpRequest.newBuilder(
                URI.create(String.format(url, clientZipCode)))
                .header("accept", "application/json")
                .build();
    }

}