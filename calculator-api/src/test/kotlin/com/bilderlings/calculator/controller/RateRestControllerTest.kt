package com.bilderlings.calculator.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation::class)
class RateRestControllerTest {

    @Autowired
    lateinit var context: WebApplicationContext
    lateinit var mvc: MockMvc

    @BeforeEach
    fun setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build()
        mvc.perform(post("/api/add")
                .contentType("application/json")
                .content("{\"base\":\"USD\",\"to\":\"EUR\",\"fee\":0.01}"))
                .andExpect(status().isOk)
                .andExpect(content().json("{\"id\":1,\"base\":\"USD\",\"to\":\"EUR\",\"fee\":0.01}"))
    }

    @Test
    @Order(1)
    fun getRates() {
        mvc.perform(get("/api/rates"))
                .andExpect(status().isOk)
                .andExpect(content().json("[{\"id\":1,\"base\":\"USD\",\"to\":\"EUR\",\"fee\":0.01}]"))
    }

    @Test
    @Order(2)
    fun addRate() {
        mvc.perform(post("/api/add")
                .contentType("application/json")
                .content("{\"base\":\"EUR\",\"to\":\"USD\",\"fee\":0.02}"))
                .andExpect(status().isOk)
                .andExpect(content().json("{\"id\":2,\"base\":\"EUR\",\"to\":\"USD\",\"fee\":0.02}"))

        mvc.perform(delete("/api/remove/2"))
                .andExpect(status().isOk)
    }

    @Test
    @Order(3)
    fun updateRate() {
        mvc.perform(post("/api/add")
                .contentType("application/json")
                .content("{\"base\":\"USD\",\"to\":\"EUR\",\"fee\":0.04}"))
                .andExpect(status().isOk)
                .andExpect(content().json("{\"id\":1,\"base\":\"USD\",\"to\":\"EUR\",\"fee\":0.04}"))
    }

    @Test
    @Order(4)
    fun removeRate() {
        mvc.perform(delete("/api/remove/1"))
                .andExpect(status().isOk)

        mvc.perform(delete("/api/remove/1"))
                .andExpect(status().isNotFound)

        mvc.perform(get("/api/rates"))
                .andExpect(status().isOk)
                .andExpect(content().json("[]"))
    }
}