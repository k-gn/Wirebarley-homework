let country = "KRW";
let exchangeMoney = 0;

const changeCountry = (target) => {
    if(target) {
        country = target.value;
    }

    $.ajax({
        type: "Get",
        url: `/exchange/today?country=${country}&exchangeMoney=${0}`,
        contentType: "application/x-www-form-urlencoded; charset=utf-8"
    }).done(res => {
        country = res.data.country;
        exchangeMoney = res.data.exchangeMoney;
        showTodayExchangeMoney();
    }).fail(err => {
        alert(err.responseJSON.message);
    })
}

function showTodayExchangeMoney() {
    const rateBox = document.querySelector(".rateBox");
    rateBox.innerHTML = `${exchangeMoney} ${country}/USD`;
}

function showCalcExchangeMoney(exchangeResponse) {
    const calcBox = document.querySelector(".calcBox");
    calcBox.innerHTML = `수취금액은 ${exchangeResponse.exchangeMoney} ${exchangeResponse.country} 입니다.`;
}

function exchange() {
    const exchangeMoneyInput = document.querySelector("#exchangeMoneyInput");
    const numCheck = /^[0-9]+$/;
    if(numCheck.test(exchangeMoneyInput.value)) {
        $.ajax({
            type: "Get",
            url: `/exchange/calc?country=${country}&exchangeMoney=${exchangeMoneyInput.value}`,
            contentType: "application/x-www-form-urlencoded; charset=utf-8"
        }).done(res => {
            showCalcExchangeMoney(res.data);
        }).fail(err => {
            alert(err.responseJSON.message);
        })
    }else {
        alert("송금액이 바르지 않습니다.")
    }
}