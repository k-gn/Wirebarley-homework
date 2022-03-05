package kr.co.wirebarley.homework.util.api;

import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
public class CurrencyAPI {

    @Value("${currency.api.id}")
    private String currencyAPIId;

    @Value("${currency.api.key}")
    private String currencyAPIKey;

    public String makeCalculateCurrencyAPIUrl(ExchangeRequest exchangeRequest) {
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

    public String makeTodayCurrencyAPIUrl(ExchangeRequest exchangeRequest, String today) {
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

    public ResponseEntity<String> requestCurrencyAPI(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(makeCurrencyHeader());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response;
    }

    public String changeTodayCurrencyDateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(verifyCurrencyStart()) {
            LocalDateTime todayDate = LocalDateTime.now();
            return todayDate.format(formatter);
        }else {
            LocalDateTime previousDate = LocalDateTime.now().minusDays(1);
            return previousDate.format(formatter);
        }
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

    private boolean verifyCurrencyStart() {
        LocalDateTime todayDate = LocalDateTime.now();
        LocalDateTime startCurrencyTime = LocalDateTime.of(todayDate.getYear(), todayDate.getMonth(), todayDate.getDayOfMonth(), 9, 0, 0);
        return todayDate.isAfter(startCurrencyTime);
    }
}
