package kr.co.wirebarley.homework.service;

import kr.co.wirebarley.homework.constant.Country;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("서비스 테스트")
@SpringBootTest
class ExchangeRateServiceImplTest {

    private final ExchangeRateService exchangeRateService;

    ExchangeRateServiceImplTest(@Autowired ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @DisplayName("1. 환율 정보에 따른 금액 계산 API 테스트")
    @Test
    void givenExchangeRequest_whenCalculateExchangeRate_thenReturnsExchangeResult() {

        // given
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .exchangeMoney(100)
                .country(Country.KRW)
                .build();

        // when
        ExchangeResponse exchangeResponse = exchangeRateService.getCalculateExchangeRate(exchangeRequest);

        // then
        assertNotNull(exchangeResponse);
        assertThat(exchangeResponse)
                .hasFieldOrProperty("exchangeMoney").isNotNull()
                .hasFieldOrPropertyWithValue("country", "KRW");
    }

    @DisplayName("2. 오늘의 환율 정보 조회 API 테스트")
    @Test
    void givenExchangeRequest_whenGetTodayExchangeRate_thenReturnsExchangeResult() {

        // given
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .exchangeMoney(100)
                .country(Country.KRW)
                .build();

        // when
        ExchangeResponse exchangeResponse = exchangeRateService.getTodayExchangeRate(exchangeRequest);

        // then
        assertNotNull(exchangeResponse);
        assertThat(exchangeResponse)
                .hasFieldOrProperty("exchangeMoney").isNotNull()
                .hasFieldOrPropertyWithValue("country", "KRW");
    }
}