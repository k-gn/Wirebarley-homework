package kr.co.wirebarley.homework.dto.exchange;

import kr.co.wirebarley.homework.constant.Country;
import lombok.*;

import java.text.DecimalFormat;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeResponse {

    private String exchangeMoney;
    private String country;

    public static ExchangeResponse resultToResponse(ExchangeResult<ExchangeResultTo> exchangeResult) {

        DecimalFormat df = new DecimalFormat("#,###.00");
        return ExchangeResponse.builder()
                .exchangeMoney(df.format(exchangeResult.getTo().get(0).getMid()))
                .country(exchangeResult.getTo().get(0).getQuotecurrency())
                .build();
    }
}
