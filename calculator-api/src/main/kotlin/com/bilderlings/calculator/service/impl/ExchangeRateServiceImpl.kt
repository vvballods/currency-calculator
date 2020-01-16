package com.bilderlings.calculator.service.impl

import com.bilderlings.calculator.data.BaseRates
import com.bilderlings.calculator.service.ExchangeRateService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.util.*

@Service
class ExchangeRateServiceImpl : ExchangeRateService {
    private final var log: Logger = LoggerFactory.getLogger(ExchangeRateServiceImpl::class.java)

    @Value("\${exchange.rates.api.url}")
    private val url: String? = null

    override fun getRates(base: Currency): BaseRates {
        log.debug("Retrieving exchange rates for ${base.currencyCode}")
        val baseRates = (RestTemplate().getForObject(url!!, BaseRates::class.java)
                ?: throw RestClientException("Exchange rates were not retrieved"))
        log.debug("Retrieved ${baseRates.rates.size} exchange rates for ${base.currencyCode}")
        return baseRates
    }

    override fun getRate(base: Currency, to: Currency): BigDecimal {
        log.debug("Retrieving conversion rate for ${base.currencyCode}->${to.currencyCode}")
        return getRates(base).rates[to.currencyCode]
                ?: throw RuntimeException("Conversion rate for ${base.currencyCode}->${to.currencyCode} was not found")
    }
}