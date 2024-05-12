package com.example.cryptopriceapi.service.impl;

import com.example.cryptopriceapi.exception.EntityNotFoundException;
import com.example.cryptopriceapi.model.CryptoCurrency;
import com.example.cryptopriceapi.service.CryptoCurrencyService;
import com.example.cryptopriceapi.util.ApiDataDownloader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {
    private final ApiDataDownloader dataDownloader;
    private List<CryptoCurrency> cryptoCurrencies;

    @Override
    public String getPrice(String cryptoCurrencyPair) {
        if (cryptoCurrencies == null) {
            cryptoCurrencies = dataDownloader.updatePrices();
        }

        CryptoCurrency cryptoCurrency = getCryptoCurrency(cryptoCurrencyPair);
        Long timeNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        if (cryptoCurrency.getNextFundingTime() - timeNow < 0) {
            cryptoCurrencies = dataDownloader.updatePrices();
            cryptoCurrency = getCryptoCurrency(cryptoCurrencyPair);
        }
        return cryptoCurrency.getMarkPrice();
    }

    private CryptoCurrency getCryptoCurrency(String cryptoCurrencyPair) {
        return cryptoCurrencies.stream()
                .filter(e -> e.getSymbol().equalsIgnoreCase(cryptoCurrencyPair))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "There is no cryptocurrency pair " + cryptoCurrencyPair));
    }
}
