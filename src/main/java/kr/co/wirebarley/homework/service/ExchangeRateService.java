package kr.co.wirebarley.homework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResult;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResultTo;

public interface ExchangeRateService {

    public ExchangeResult<ExchangeResultTo> convertToExchangeRate(ExchangeRequest exchangeRequest);
    public ExchangeResult<ExchangeResultTo> getTodayExchangeRate(ExchangeRequest exchangeRequest);
}
