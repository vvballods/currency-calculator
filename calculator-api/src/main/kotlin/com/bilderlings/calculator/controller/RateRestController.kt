package com.bilderlings.calculator.controller

import com.bilderlings.calculator.model.Rate
import com.bilderlings.calculator.service.RateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin("\${allowed.origins}")
@RequestMapping("/api")
class RateRestController(private val rateService: RateService) {

    @GetMapping("/rates")
    fun getRates(): List<Rate> {
        return rateService.all()
    }

    @PostMapping("/add")
    fun addRate(@Valid @RequestBody rate: Rate): Rate {
        return rateService.addOrUpdate(rate)
    }

    @DeleteMapping("/remove/{id}")
    fun removeRate(@PathVariable id: Long): ResponseEntity<Unit> {
        if (rateService.exits(id)) {
            rateService.remove(id)
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/calculate")
    fun calculateRate(@RequestParam amount: Long, @RequestParam from: String, @RequestParam to: String): BigDecimal {
        return rateService.calculate(BigDecimal(amount), Currency.getInstance(from), Currency.getInstance(to))
    }
}