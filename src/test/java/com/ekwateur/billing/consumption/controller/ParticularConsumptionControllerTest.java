package com.ekwateur.billing.consumption.controller;

import com.ekwateur.billing.consumption.service.ParticularConsumption;
import com.ekwateur.billing.energy.EnergyType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticularConsumptionController.class)
@AutoConfigureMockMvc
class ParticularConsumptionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ParticularConsumption particularConsumption;

    @Test
    void shouldRegisterCustomerConsumption() throws Exception {
        // Given
        ConsumptionRequestModel consumptionRequestModel = new ConsumptionRequestModel(
                "EWK12345678",
                EnergyType.ELECTRICITY,
                LocalDateTime.now(),
                10.0
        );


        mockMvc.perform(post("/api/v1/consumptions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(consumptionRequestModel)))
                .andExpect(status().isCreated());
    }
}