package com.example.cryptopriceapi.model;

import lombok.Data;

@Data
public class CryptoCurrency {
    private String symbol;
    private String markPrice;
    private Long nextFundingTime;
}
