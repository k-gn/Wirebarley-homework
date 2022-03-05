package kr.co.wirebarley.homework.dto.exchange;

import lombok.*;

import java.text.DecimalFormat;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeResponse {

    private String exchangeMoney;
    private String country;

    public static ExchangeResponse resultToResponse(ExchangeResult<ExchangeResultTo> exchangeResult) {

        DecimalFormat df = new DecimalFormat("#,###.00");
        ExchangeResultTo exchangeResultTo = exchangeResult.getTo().stream().findFirst().get();
        return ExchangeResponse.builder()
                .exchangeMoney(df.format(exchangeResultTo.getMid()))
                .country(exchangeResultTo.getQuotecurrency())
                .build();
    }
}
