package kr.co.wirebarley.homework.dto.exchange;

import kr.co.wirebarley.homework.constant.Country;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRequest {

    private Long exchangeMoney;
    private Country country;
}
