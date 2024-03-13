package com.ekwateur.billing.consumption.controller;

import com.ekwateur.billing.energy.EnergyType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ConsumptionRequestModel(
        @NotNull
        @Size(min = 11, max = 11, message = "reference must have 11 character")
        String customerReference,
        EnergyType energyType,
        LocalDateTime registeredAt,
        Double total) {
}
