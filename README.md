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
├─main
│  ├─java
│  │  └─kr
│  │      └─co
│  │          └─wirebarley
│  │              └─homework
│  │                  │  WirebarleyHomeworkApplication.java
│  │                  │
│  │                  ├─constant (상수 패키지)
│  │                  │      Country.java
│  │                  │      ErrorCode.java
│  │                  │
│  │                  ├─controller (컨트롤러 패키지)
│  │                  │  │  ExchangeRateController.java
│  │                  │  │
│  │                  │  └─api
│  │                  │       APIExchangeRateController.java
│  │                  │
│  │                  ├─dto (DTO 패키지)
│  │                  │  │  APIDataResponse.java
│  │                  │  │  APIErrorResponse.java
│  │                  │  │
│  │                  │  └─exchange
│  │                  │          ExchangeRequest.java
│  │                  │          ExchangeResponse.java
│  │                  │          ExchangeResult.java
│  │                  │          ExchangeResultTo.java
│  │                  │
│  │                  ├─exception (예외처리 패키지)
│  │                  │  │  GeneralException.java
│  │                  │  │
│  │                  │  └─handler
│  │                  │          APIExceptionHandler.java
│  │                  │
│  │                  ├─service (서비스 패키지)
│  │                  │      ExchangeRateService.java
│  │                  │      ExchangeRateServiceImpl.java
│  │                  │
│  │                  └─util (유틸 패키지)
│  │                      └─api
│  │                           CurrencyAPI.java
│  │
│  └─resources
│      │  application.yml
│      │
│      ├─static
│      │  └─js
│      │       exchange.js
│      │       jquery-3.5.1.js
│      │
│      └─templates
│              exchangeRate.html
│
└─test
    └─java
        └─kr
            └─co
                └─wirebarley
                    └─homework
                        │  WirebarleyHomeworkApplicationTests.java
                        │
                        ├─controller
                        │  │  ExchangeRateControllerTest.java
                        │  │
                        │  └─api
                        │       APIExchangeRateControllerTest.java
                        │
                        └─service
                                ExchangeRateServiceImplTest.java
***


