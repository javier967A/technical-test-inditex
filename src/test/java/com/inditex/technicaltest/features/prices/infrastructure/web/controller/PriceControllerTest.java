package com.inditex.technicaltest.features.prices.infrastructure.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PriceControllerTest {

    @Autowired private MockMvc mockMvc;

    // Ajusta el path si tu controlador expone otro endpoint
    private static final String URL = "/api/v1/prices";

    @Test
    @DisplayName("Test 1: 2020-06-14 10:00 -> priceList 1, 35.50")
    void test1() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate","2020-06-14T10:00:00")
                        .param("productId","35455")
                        .param("brandId","1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(35.5))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Test 2: 2020-06-14 16:00 -> priceList 2, 25.45")
    void test2() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate","2020-06-14T16:00:00")
                        .param("productId","35455")
                        .param("brandId","1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Test 3: 2020-06-14 21:00 -> priceList 1, 35.50")
    void test3() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate","2020-06-14T21:00:00")
                        .param("productId","35455")
                        .param("brandId","1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.5));
    }

    @Test
    @DisplayName("Test 4: 2020-06-15 10:00 -> priceList 3, 30.50")
    void test4() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate","2020-06-15T10:00:00")
                        .param("productId","35455")
                        .param("brandId","1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.5));
    }

    @Test
    @DisplayName("Test 5: 2020-06-16 21:00 -> priceList 4, 38.95")
    void test5() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate","2020-06-16T21:00:00")
                        .param("productId","35455")
                        .param("brandId","1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    @DisplayName("Not found -> 404 con payload de error")
    void notFound_404() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate","2020-06-13T10:00:00")
                        .param("productId","35455")
                        .param("brandId","1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").exists());
    }
}
