package com.bilderlings.calculator.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

@Entity
data class Rate(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,

        @NotBlank
        @Size(min = 3, max = 3)
        val base: Currency,

        @NotBlank
        @Size(min = 3, max = 3)
        val to: Currency,

        @NotNull
        @PositiveOrZero
        val fee: BigDecimal
)
