package com.example.cryptopriceapi.util;

import com.example.cryptopriceapi.exception.DataProcessException;
import com.example.cryptopriceapi.model.CryptoCurrency;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiDataDownloader {
    private static final String BINANCE_API_URL = "https://fapi.binance.com/fapi/v1/premiumIndex";
    private final ObjectMapper jasonMapper;
    private final HttpClient client;

    public List<CryptoCurrency> updatePrices() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BINANCE_API_URL))
                .build();
        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            return List.of(jasonMapper.readValue(response.body(), CryptoCurrency[].class));
        } catch (IOException | InterruptedException e) {
            throw new DataProcessException("Cannot get data from API", e);
        }
    }
}
