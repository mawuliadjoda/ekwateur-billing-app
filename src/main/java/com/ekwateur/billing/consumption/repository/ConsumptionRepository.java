package com.ekwateur.billing.consumption.repository;

import com.ekwateur.billing.consumption.Consumption;
import com.ekwateur.billing.energy.EnergyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
    @Query(" SELECT cons from Consumption cons JOIN cons.customer cu  JOIN cons.energy e where cu.reference =:reference and e.type =:energyType and year(cons.registeredAt) =:year and month(cons.registeredAt) =:month")
    Optional<Consumption> findConsumption(String reference, EnergyType energyType, int year, int month);
}
