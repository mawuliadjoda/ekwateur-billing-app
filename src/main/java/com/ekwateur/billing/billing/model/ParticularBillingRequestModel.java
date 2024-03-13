package com.ekwateur.billing.billing.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ParticularBillingRequestModel(
        @NotNull
        @Size(min = 11, max = 11, message = "reference must have 11 character")
        String reference,
        int month,
        int year
         // ,double electricityConsumption,
         // double gazConsumption
) {
}
