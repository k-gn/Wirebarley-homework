package kr.co.wirebarley.homework.controller.api;

import kr.co.wirebarley.homework.dto.APIDataResponse;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import kr.co.wirebarley.homework.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange")
public class APIExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/today")
    public ResponseEntity<?> getTodayExchangeRate(@Valid ExchangeRequest exchangeRequest) {

        return ResponseEntity.ok(APIDataResponse.of(exchangeRateService.getTodayExchangeRate(exchangeRequest)));
    }

    @GetMapping("/calc")
    public ResponseEntity<?> calculateExchangeRate(@Valid ExchangeRequest exchangeRequest) {

        return ResponseEntity.ok(APIDataResponse.of(exchangeRateService.calculateExchangeRate(exchangeRequest)));
    }

}
