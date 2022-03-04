package kr.co.wirebarley.homework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResponse;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResult;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResultTo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Value("${currency.api.id}")
    private String currencyAPIId;

    @Value("${currency.api.key}")
    private String currencyAPIKey;

    @Override
    public ExchangeResponse calculateExchangeRate(ExchangeRequest exchangeRequest) {
        String url = makeCurrencyAPIUrl(exchangeRequest);
        ResponseEntity<String> response = requestCurrencyAPI(url);
        ExchangeResult<ExchangeResultTo> exchangeResult = currencyJsonToExchangeResult(response);
        return ExchangeResponse.resultToResponse(exchangeResult);
    }

    @Override
    public ExchangeResponse getTodayExchangeRate(ExchangeRequest exchangeRequest) {
        String url = makeCurrencyAPIUrl(exchangeRequest, changeTodayCurrencyDateToString());
        ResponseEntity<String> response = requestCurrencyAPI(url);
        ExchangeResult<ExchangeResultTo> exchangeResult = currencyJsonToExchangeResult(response);
        return ExchangeResponse.resultToResponse(exchangeResult);
    }

    private String makeCurrencyAPIUrl(ExchangeRequest exchangeRequest) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("xecdapi.xe.com")
                .path("/v1/convert_from.json")
                .queryParam("from", "USD")
                .queryParam("to", exchangeRequest.getCountry())
                .queryParam("amount", exchangeRequest.getExchangeMoney())
                .build(true);
        return uriComponents.toString();
    }

    private String makeCurrencyAPIUrl(ExchangeRequest exchangeRequest, String today) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("xecdapi.xe.com")
                .path("/v1/historic_rate.json")
                .queryParam("from", "USD")
                .queryParam("date", today)
                .queryParam("to", exchangeRequest.getCountry())
                .build(true);
        return uriComponents.toString();
    }

    private ResponseEntity<String> requestCurrencyAPI(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(makeCurrencyHeader());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response;
    }

    private ExchangeResult<ExchangeResultTo> currencyJsonToExchangeResult(ResponseEntity<String> response) {
        ExchangeResult<ExchangeResultTo> exchangeResult = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            exchangeResult = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {}
        return exchangeResult;
    }

    private String makeCurrencyToken() {
        String tokenString =  currencyAPIId.trim() + ":" + currencyAPIKey.trim();
        String encodeToken = Base64.getEncoder().encodeToString(tokenString.getBytes());
        return encodeToken;
    }

    private HttpHeaders makeCurrencyHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Basic " + makeCurrencyToken());
        return headers;
    }

    private String changeTodayCurrencyDateToString() {
        String today = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(verifyCurrencyStart()) {
            LocalDateTime todayDate = LocalDateTime.now();
            today = todayDate.format(formatter);
        }else {
            LocalDateTime previousDate = LocalDateTime.now().minusDays(1);
            today = previousDate.format(formatter);
        }
        return today;
    }

    private boolean verifyCurrencyStart() {
        LocalDateTime todayDate = LocalDateTime.now();
        LocalDateTime startCurrencyTime = LocalDateTime.of(todayDate.getYear(), todayDate.getMonth(), todayDate.getDayOfMonth(), 9, 0, 0);
        return todayDate.isAfter(startCurrencyTime);
    }
}
