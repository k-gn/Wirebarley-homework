package kr.co.wirebarley.homework.controller.api;

import kr.co.wirebarley.homework.constant.Country;
import kr.co.wirebarley.homework.constant.ErrorCode;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class APIExchangeRateControllerTest {

    private final MockMvc mvc;

    public APIExchangeRateControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[API][GET] 오늘의 환율 정보 조회")
    @Test
    void givenExchangeRequest_whenRequestGetTodayExchangeRate_thenReturnsExchangeResultInStandardResponse() throws Exception {
        // given
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .exchangeMoney(100)
                .country(Country.KRW)
                .build();

        // when & then
        mvc.perform(get("/exchange/today")
                .queryParam("exchangeMoney", String.valueOf(exchangeRequest.getExchangeMoney()))
                .queryParam("country", exchangeRequest.getCountry().name())
            )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.country").value("KRW"))
                .andExpect(jsonPath("$.data.exchangeMoney").isNotEmpty())
                .andExpect(jsonPath("$.data.exchangeMoney").isString())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                .andDo(print());
    }

    @DisplayName("[API][GET] 환율 계산 정보 조회")
    @Test
    void givenExchangeRequest_whenRequestCalculateExchangeRate_thenReturnsExchangeResultInStandardResponse() throws Exception {
        // given
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .exchangeMoney(100)
                .country(Country.KRW)
                .build();

        // when & then
        mvc.perform(get("/exchange/calc")
                .queryParam("exchangeMoney", String.valueOf(exchangeRequest.getExchangeMoney()))
                .queryParam("country", exchangeRequest.getCountry().name())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.country").value("KRW"))
                .andExpect(jsonPath("$.data.exchangeMoney").isNotEmpty())
                .andExpect(jsonPath("$.data.exchangeMoney").isString())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                .andDo(print());
    }

    @DisplayName("[API][GET] 잘못된 값으로 환율 계산 정보 조회")
    @Test
    void givenBadExchangeRequest_whenRequestCalculateExchangeRate_thenReturnsValidException() throws Exception {
        // given
        ExchangeRequest exchangeRequest = ExchangeRequest.builder()
                .exchangeMoney(100000)
                .country(Country.KRW)
                .build();

        // when & then
        MvcResult mvcResult = mvc.perform(get("/exchange/calc")
                .queryParam("exchangeMoney", String.valueOf(exchangeRequest.getExchangeMoney()))
                .queryParam("country", exchangeRequest.getCountry().name())
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("송금액이 바르지 않습니다."))
                .andReturn();
    }
}