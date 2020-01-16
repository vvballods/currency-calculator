package com.bilderlings.calculator.service.impl

import com.bilderlings.calculator.model.Rate
import com.bilderlings.calculator.repository.RateRepository
import com.bilderlings.calculator.service.ExchangeRateService
import com.bilderlings.calculator.service.RateService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP
import java.util.*

@Service
class RateServiceImpl(private val rateRepository: RateRepository,
                      private val exchangeRateService: ExchangeRateService) : RateService {
    private final var log: Logger = LoggerFactory.getLogger(RateServiceImpl::class.java)

    @Value("\${exchange.default.fee}")
    private val defaultFee: BigDecimal = BigDecimal(0.01)

    override fun all(): List<Rate> {
        return rateRepository.findAll()
    }

    override fun addOrUpdate(rate: Rate): Rate {
        log.debug("Adding updated exchange rate for ${rate.base}->${rate.to} with fee ${rate.fee}")
        return rateRepository.findByBaseAndTo(rate.base, rate.to)
                ?.let { oldRate -> rateRepository.save(Rate(oldRate.id, rate.base, rate.to, rate.fee)) }
                ?: rateRepository.save(rate)
    }

    override fun exits(id: Long): Boolean {
        return rateRepository.findById(id).isPresent
    }

    override fun remove(id: Long) {
        rateRepository.deleteById(id)
    }

    override fun calculate(amount: BigDecimal, base: Currency, to: Currency): BigDecimal {
        val rateAmount = exchangeRateService.getRate(base, to)
        val rate = rateRepository.findByBaseAndTo(base, to)
                ?.let {
                    log.debug("Conversion fee ${it.fee} is applied for ${base.currencyCode}->${to.currencyCode}")
                    amount.multiply(rateAmount.minus(it.fee))
                }
                ?: run {
                    log.debug("Default conversion fee $defaultFee is applied for ${base.currencyCode}->${to.currencyCode}")
                    amount.multiply(rateAmount.minus(defaultFee))
                }
        return rate.setScale(2, HALF_UP)
    }
}