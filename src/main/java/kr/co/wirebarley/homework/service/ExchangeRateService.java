package kr.co.wirebarley.homework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResponse;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResult;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResultTo;

public interface ExchangeRateService {

    public ExchangeResponse calculateExchangeRate(ExchangeRequest exchangeRequest);
    public ExchangeResponse getTodayExchangeRate(ExchangeRequest exchangeRequest);
}
