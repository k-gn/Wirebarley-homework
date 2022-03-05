# wirebarley Homework

## Description

> 2022.02.28 - 2022.03.07.

* 와이어바알리 과제 진행
* 스프링을 사용하여 환율계산 웹 기능 만들기
  <br>
  
## About Project

* java 11 / gradle / IntelliJ / springboot 2.5.10

> Dependency
* spring-boot-starter-web
* spring-boot-starter-validation
* spring-boot-starter-test
* spring-boot-starter-thymeleaf
* lombok

### Structure
├─main <br>
│  ├─java <br>
│  │  └─kr <br>
│  │      └─co <br>
│  │          └─wirebarley <br>
│  │              └─homework <br>
│  │                  │  WirebarleyHomeworkApplication.java <br>
│  │                  │ <br>
│  │                  ├─constant (상수 패키지) <br>
│  │                  │      Country.java <br>
│  │                  │      ErrorCode.java <br>
│  │                  │ <br>
│  │                  ├─controller (컨트롤러 패키지) <br>
│  │                  │  │  ExchangeRateController.java <br>
│  │                  │  │ <br> 
│  │                  │  └─api <br>
│  │                  │       APIExchangeRateController.java <br>
│  │                  │ <br> 
│  │                  ├─dto (DTO 패키지) <br>
│  │                  │  │  APIDataResponse.java <br>
│  │                  │  │  APIErrorResponse.java <br>
│  │                  │  │ <br>
│  │                  │  └─exchange <br>
│  │                  │          ExchangeRequest.java <br>
│  │                  │          ExchangeResponse.java <br>
│  │                  │          ExchangeResult.java <br>
│  │                  │          ExchangeResultTo.java <br>
│  │                  │ <br>
│  │                  ├─exception (예외처리 패키지) <br>
│  │                  │  │  GeneralException.java <br>
│  │                  │  │ <br>
│  │                  │  └─handler <br>
│  │                  │          APIExceptionHandler.java <br>
│  │                  │ <br>
│  │                  ├─service (서비스 패키지) <br>
│  │                  │      ExchangeRateService.java <br>
│  │                  │      ExchangeRateServiceImpl.java <br>
│  │                  │ <br>
│  │                  └─util (유틸 패키지) <br>
│  │                      └─api <br>
│  │                           CurrencyAPI.java <br>
│  │ <br>
│  └─resources <br> 
│      │  application.yml <br>
│      │ <br>
│      ├─static <br>
│      │  └─js <br>
│      │       exchange.js <br>
│      │       jquery-3.5.1.js <br>
│      │ <br>
│      └─templates <br>
│              exchangeRate.html <br>
│ <br>
└─test <br>
    └─java <br>
        └─kr <br>
            └─co <br>
                └─wirebarley <br>
                    └─homework <br>
                        │  WirebarleyHomeworkApplicationTests.java <br>
                        │ <br>
                        ├─controller <br>
                        │  │  ExchangeRateControllerTest.java <br>
                        │  │ <br>
                        │  └─api <br>
                        │       APIExchangeRateControllerTest.java <br>
                        │ <br>
                        └─service <br>
                                ExchangeRateServiceImplTest.java <br>
***


