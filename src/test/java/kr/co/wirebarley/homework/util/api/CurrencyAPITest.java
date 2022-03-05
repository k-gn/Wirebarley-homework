package kr.co.wirebarley.homework.util.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.wirebarley.homework.constant.Country;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResult;
import kr.co.wirebarley.homework.dto.exchange.ExchangeResultTo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CurrencyAPITest {

    private final CurrencyAPI currencyAPI;

    CurrencyAPITest(@Autowired CurrencyAPI currencyAPI) {
        this.currencyAPI = currencyAPI;
    }

    @DisplayName("[API] 환율 계산 API 테스트")
    @Test
    void givenExchangeRequest_whenRequestCurrencyAPI_thenReturnsResponseEntity() throws JsonProcessingException {

        // given
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .exchangeMoney(100)
                .country(Country.KRW)
                .build();

        // when
        String url = currencyAPI.makeCalculateCurrencyAPIUrl(exchangeRequest);
        ResponseEntity<String> response = currencyAPI.requestCurrencyAPI(url);

        // then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getStatusCodeValue(), 200);

        ObjectMapper objectMapper = new ObjectMapper();
        ExchangeResult<ExchangeResultTo> exchangeResult = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(exchangeResult)
                .hasFieldOrPropertyWithValue("from", "USD");
        assertThat(exchangeResult.getTo())
                .isNotEmpty()
                .hasSize(1);
        assertThat(exchangeResult.getTo().stream().findFirst().get())
                .hasFieldOrPropertyWithValue("quotecurrency", "KRW")
                .hasFieldOrProperty("mid").isNotNull();
    }

    @DisplayName("[API] 오늘의 환율 정보 조회 API 테스트")
    @Test
    void givenExchangeRequest_whenRequestTodayCurrencyAPI_thenReturnsResponseEntity() throws JsonProcessingException {

        // given
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .exchangeMoney(100)
                .country(Country.KRW)
                .build();

        // when
        String url = currencyAPI.makeTodayCurrencyAPIUrl(exchangeRequest, currencyAPI.changeTodayCurrencyDateToString());
        ResponseEntity<String> response = currencyAPI.requestCurrencyAPI(url);

        // then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getStatusCodeValue(), 200);

        ObjectMapper objectMapper = new ObjectMapper();
        ExchangeResult<ExchangeResultTo> exchangeResult = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        assertThat(exchangeResult)
                .hasFieldOrPropertyWithValue("from", "USD");
        assertThat(exchangeResult.getTo())
                .isNotEmpty()
                .hasSize(1);
        assertThat(exchangeResult.getTo().stream().findFirst().get())
                .hasFieldOrPropertyWithValue("quotecurrency", "KRW")
                .hasFieldOrProperty("mid").isNotNull();
    }
}