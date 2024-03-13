package com.ekwateur.billing.consumption.controller;

import com.ekwateur.billing.consumption.service.ParticularConsumption;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/consumptions")
@RequiredArgsConstructor
public class ParticularConsumptionController {

    private final ParticularConsumption particularConsumption;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void registerConsumption(@RequestBody @Validated ConsumptionRequestModel model) {
        particularConsumption.accept(model);
    }
}
