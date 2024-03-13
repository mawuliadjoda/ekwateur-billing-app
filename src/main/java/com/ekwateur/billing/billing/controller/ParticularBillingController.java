package com.ekwateur.billing.billing.controller;

import com.ekwateur.billing.billing.model.ParticularBillingRequestModel;
import com.ekwateur.billing.billing.model.ParticularBillingResponseModel;
import com.ekwateur.billing.billing.service.ParticularBillingCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/particular-billings")
@RequiredArgsConstructor
public class ParticularBillingController {

    private final ParticularBillingCalculator particularBillingCalculator;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ParticularBillingResponseModel calculateBilling(@RequestBody @Validated ParticularBillingRequestModel request) {
        return particularBillingCalculator.apply(request);
    }
}
