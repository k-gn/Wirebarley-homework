package kr.co.wirebarley.homework.dto.exchange;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExchangeResultTo {

    private String quotecurrency;

    private Double mid;

}
