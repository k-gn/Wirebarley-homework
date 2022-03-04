package kr.co.wirebarley.homework.dto.exchange;

import kr.co.wirebarley.homework.constant.Country;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExchangeRequest {

    @NotNull(message = "송금액이 바르지 않습니다.")
    @Max(value = 10000, message = "송금액이 바르지 않습니다.")
    @Min(value = 0, message = "송금액이 바르지 않습니다.")
    private Integer exchangeMoney;

    @NotNull(message = "수취국가를 선택해주세요.")
    private Country country;
}
