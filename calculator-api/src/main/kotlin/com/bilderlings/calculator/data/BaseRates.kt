package com.bilderlings.calculator.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
data class BaseRates(val base: String, val rates: Map<String, BigDecimal>)