package kr.co.wirebarley.homework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Value("${currency.api.id}")
    private String currencyAPIId;

    @Value("${currency.api.key}")
    private String currencyAPIKey;

    @Override
    public ExchangeResult<ExchangeResultTo> convertToExchangeRate(ExchangeRequest exchangeRequest) {
        String url = "https://xecdapi.xe.com/v1/convert_from.json/?from=USD&to=KRW&amount=100";
        ResponseEntity<String> response = requestCurrencyAPI(url);
        ExchangeResult<ExchangeResultTo> exchangeResult = currencyJsonToExchangeResult(response);
        return exchangeResult;
    }

    @Override
    public ExchangeResult<ExchangeResultTo> getTodayExchangeRate(ExchangeRequest exchangeRequest) {
        String today = todayDateToString();
        String url = "https://xecdapi.xe.com/v1/historic_rate.json/?from=USD&date=2022-03-03&to=KRW";
        ResponseEntity<String> response = requestCurrencyAPI(url);
        ExchangeResult<ExchangeResultTo> exchangeResult = currencyJsonToExchangeResult(response);
        return exchangeResult;
    }

    public ResponseEntity<String> requestCurrencyAPI(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(makeCurrencyHeader());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response;
    }

    public ExchangeResult<ExchangeResultTo> currencyJsonToExchangeResult(ResponseEntity<String> response) {
        ExchangeResult<ExchangeResultTo> exchangeResult = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            exchangeResult = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
//            throw new
        }
        return exchangeResult;
    }

    public String makeCurrencyToken() {
        String tokenString =  currencyAPIId.trim() + ":" + currencyAPIKey.trim();
        String encodeToken = Base64.getEncoder().encodeToString(tokenString.getBytes());
        return encodeToken;
    }

    public HttpHeaders makeCurrencyHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Basic " + makeCurrencyToken());
        return headers;
    }

    public String todayDateToString() {
        LocalDate todayDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = todayDate.format(formatter);
        return today;
    }
}
