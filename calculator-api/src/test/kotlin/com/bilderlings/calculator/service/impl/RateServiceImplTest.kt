package com.bilderlings.calculator.service.impl

import com.bilderlings.calculator.model.Rate
import com.bilderlings.calculator.repository.RateRepository
import com.bilderlings.calculator.service.ExchangeRateService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP
import java.util.*

@SpringBootTest
class RateServiceImplTest {

    @InjectMocks
    lateinit var rateService: RateServiceImpl
    @Mock
    lateinit var rateRepository: RateRepository
    @Mock
    lateinit var exchangeRateService: ExchangeRateService

    @Test
    fun calculate() {
        val base = Currency.getInstance("EUR")
        val to = Currency.getInstance("USD")
        val exchangeRate = BigDecimal(1.11)
        val rate = Rate(1, base, to, BigDecimal(0.01))
        `when`(rateRepository.findByBaseAndTo(base, to)).thenReturn(rate)
        `when`(exchangeRateService.getRate(base, to)).thenReturn(exchangeRate)

        val calculatedRate = rateService.calculate(BigDecimal(1000), base, to).setScale(2, HALF_UP)

        assertEquals(BigDecimal(1100).setScale(2, HALF_UP), calculatedRate)
    }
}