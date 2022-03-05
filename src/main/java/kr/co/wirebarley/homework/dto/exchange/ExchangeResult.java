package kr.co.wirebarley.homework.dto.exchange;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExchangeResult<T> {

    private String terms;

    private String privacy;

    private String from;

    private Double amount;

    private String timestamp;

    private List<T> to;

}
