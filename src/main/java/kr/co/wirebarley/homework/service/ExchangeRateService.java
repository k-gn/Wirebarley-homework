package kr.co.wirebarley.homework.service;

import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResponse;

public interface ExchangeRateService {

    public ExchangeResponse getCalculateExchangeRate(ExchangeRequest exchangeRequest);
    public ExchangeResponse getTodayExchangeRate(ExchangeRequest exchangeRequest);
}
