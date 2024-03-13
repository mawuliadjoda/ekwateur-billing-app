package com.ekwateur.billing.billing;

import com.ekwateur.billing.billing.controller.ParticularBillingController;
import com.ekwateur.billing.billing.model.ParticularBillingRequestModel;
import com.ekwateur.billing.billing.model.ParticularBillingResponseModel;
import com.ekwateur.billing.billing.service.ParticularBillingCalculator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticularBillingController.class)
@AutoConfigureMockMvc
class ParticularBillingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ParticularBillingCalculator particularBillingCalculator;

    @Test
    void shouldCalculateAmountToBillWhenGivenValidReference() throws Exception {
        var particularBillingRequestModel = new ParticularBillingRequestModel(
                "EWK12345678",
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue()
                //, 10, 0
        );
        var particularBillingResponseModel = new ParticularBillingResponseModel("EWK12345678", 50, null);

        given(particularBillingCalculator.apply(particularBillingRequestModel)).willReturn(particularBillingResponseModel);

        mockMvc.perform(post("/api/v1/particular-billings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(particularBillingRequestModel)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(particularBillingResponseModel)));
    }

    @Test
    void shouldNotCalculateAmountToBillWhenGivenBadReference() throws Exception {
        var particularBillingRequestModel = new ParticularBillingRequestModel(
                "EWK12345678RTYUIO",
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue()
                // , 10, 0
        );

        mockMvc.perform(post("/api/v1/particular-billings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(particularBillingRequestModel)))
                .andExpect(status().isBadRequest());
    }
}