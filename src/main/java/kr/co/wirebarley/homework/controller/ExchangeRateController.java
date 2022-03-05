package kr.co.wirebarley.homework.controller;

import kr.co.wirebarley.homework.constant.Country;
import kr.co.wirebarley.homework.dto.exchange.ExchangeRequest;
import kr.co.wirebarley.homework.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping({"/", "/exchange"})
    public ModelAndView moveExchageRatePage(ModelAndView mv) {
        mv.addObject("exchangeRate", exchangeRateService.getTodayExchangeRate(
                ExchangeRequest.builder().country(Country.KRW).exchangeMoney(0).build()
            ));
        mv.setViewName("exchangeRate");
        return mv;
    }
}
