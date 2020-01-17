package com.bilderlings.calculator.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@Entity
data class Rate(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,

        @NotNull
        val base: Currency,

        @NotNull
        val to: Currency,

        @NotNull
        @PositiveOrZero
        val fee: BigDecimal
)
