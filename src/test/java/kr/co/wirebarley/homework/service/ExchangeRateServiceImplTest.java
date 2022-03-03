package kr.co.wirebarley.homework.service;

import kr.co.wirebarley.homework.constant.Country;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResult;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResultTo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceImplTest {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @DisplayName("1. convertToExchangeRate Test")
    @Test
    void convertToExchangeRate() {

        // given
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .exchangeMoney(100L)
                .country(Country.KRW)
                .build();
        Double exchangeRateMoney = 120320.2663530537;

        // when
        ExchangeResult<ExchangeResultTo> exchangeResult = exchangeRateService.convertToExchangeRate(exchangeRequest);

        // then
        assertNotNull(exchangeResult);
        assertEquals(exchangeResult.getFrom(), "USD");
        assertThat(exchangeResult.getTo())
            .hasSize(1)
            .allSatisfy(rateInfo -> {
                assertThat(rateInfo).hasFieldOrPropertyWithValue("quotecurrency", "KRW");
                assertThat(rateInfo.getMid()).isNotNull().isNotZero();
                assertEquals(rateInfo.getMid(), exchangeRateMoney);
            });
    }

    @DisplayName("2. getTodayExchangeRate Test")
    @Test
    void getTodayExchangeRate() {

        // given
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .exchangeMoney(100L)
                .country(Country.KRW)
                .build();
        Double todayRate = 1203.2026635305;

        // when
        ExchangeResult<ExchangeResultTo> todayExchangeRate = exchangeRateService.getTodayExchangeRate(exchangeRequest);

        // then
        assertNotNull(todayExchangeRate);
        assertEquals(todayExchangeRate.getFrom(), "USD");
        assertThat(todayExchangeRate.getTo())
            .hasSize(1)
            .allSatisfy(rateInfo -> {
                assertThat(rateInfo).hasFieldOrPropertyWithValue("quotecurrency", "KRW");
                assertThat(rateInfo.getMid()).isNotNull().isNotZero();
                assertEquals(rateInfo.getMid(), todayRate);
            });
    }
}