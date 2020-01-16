package com.bilderlings.calculator.service

import com.bilderlings.calculator.data.BaseRates
import java.math.BigDecimal
import java.util.*

interface ExchangeRateService {
    fun getRates(base: Currency): BaseRates
    fun getRate(base: Currency, to: Currency): BigDecimal
}