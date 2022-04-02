package kr.co.wirebarley.homework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.wirebarley.homework.constant.ErrorCode;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResponse;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResult;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResultTo;
import kr.co.wirebarley.homework.exception.GeneralException;
import kr.co.wirebarley.homework.util.api.CurrencyAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final CurrencyAPI currencyAPI;

    @Override
    public ExchangeResponse getCalculateExchangeRate(ExchangeRequest exchangeRequest) {
        String url = currencyAPI.makeCalculateCurrencyAPIUrl(exchangeRequest);
        ResponseEntity<String> response = currencyAPI.requestCurrencyAPI(url);
        ExchangeResult<ExchangeResultTo> exchangeResult = currencyJsonToExchangeResult(response);
        return ExchangeResponse.resultToResponse(exchangeResult);
    }

    @Override
    public ExchangeResponse getTodayExchangeRate(ExchangeRequest exchangeRequest) {
        String url = currencyAPI.makeTodayCurrencyAPIUrl(exchangeRequest, currencyAPI.changeTodayCurrencyDateToString());
        ResponseEntity<String> response = currencyAPI.requestCurrencyAPI(url);
        ExchangeResult<ExchangeResultTo> exchangeResult = currencyJsonToExchangeResult(response);
        return ExchangeResponse.resultToResponse(exchangeResult);
    }

    private ExchangeResult<ExchangeResultTo> currencyJsonToExchangeResult(ResponseEntity<String> response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new GeneralException(ErrorCode.INTERNAL_ERROR);
        }
    }

}
