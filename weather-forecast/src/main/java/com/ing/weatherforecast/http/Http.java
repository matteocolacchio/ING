package com.ing.weatherforecast.http;

import com.ing.weatherforecast.exception.OpenweathermapException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Http {
    public String GET(URL url) throws OpenweathermapException, IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url.toURI())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        }
        throw new OpenweathermapException("An error has occurred calling the Openweathermap API!");
    }
}
