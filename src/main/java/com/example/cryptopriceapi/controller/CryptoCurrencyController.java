package com.example.cryptopriceapi.controller;

import com.example.cryptopriceapi.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/cryptoPair")
public class CryptoCurrencyController {
    private final CryptoCurrencyService service;

    @GetMapping
    public String getPrice(@RequestParam String cryptoPair) {
        return service.getPrice(cryptoPair);
    }
}
