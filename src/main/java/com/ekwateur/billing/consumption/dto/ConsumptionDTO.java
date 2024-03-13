package com.ekwateur.billing.consumption.dto;

import com.ekwateur.billing.energy.EnergyType;

import java.time.LocalDateTime;

public record ConsumptionDTO(
        String customerReference,
        EnergyType energyType,
        LocalDateTime registeredAt,
        Double total) {
}
