package kr.co.wirebarley.homework.controller.api;

import kr.co.wirebarley.homework.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class APIExchangeRateController {

    private final ExchangeRateService exchangeRateService;
}
