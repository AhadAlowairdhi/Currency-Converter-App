package com.example.currencyconverterapp


class CurrencyModel(val date: String,val eur:Currency) {
    class Currency {
        var inr : Double? = null
        var usd : Double? = null
        var aud : Double? = null
        var sar : Double? = null
        var cny : Double? = null
        var jpy : Double? = null
    }
}
