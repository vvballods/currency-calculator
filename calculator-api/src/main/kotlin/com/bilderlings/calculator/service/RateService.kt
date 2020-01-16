package com.bilderlings.calculator.service

import com.bilderlings.calculator.model.Rate
import java.math.BigDecimal
import java.util.*

interface RateService {
    fun all(): List<Rate>
    fun addOrUpdate(rate: Rate): Rate
    fun exits(id: Long): Boolean
    fun remove(id: Long)
    fun calculate(amount: BigDecimal, base: Currency, to: Currency): BigDecimal
}