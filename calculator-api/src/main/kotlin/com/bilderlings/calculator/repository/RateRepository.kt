package com.bilderlings.calculator.repository

import com.bilderlings.calculator.model.Rate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RateRepository : JpaRepository<Rate, Long> {
    fun findByBaseAndTo(base: Currency, to: Currency): Rate?
}