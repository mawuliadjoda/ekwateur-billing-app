package com.ekwateur.billing.energy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnergyRepository extends JpaRepository<Energy, Long> {
    Optional<Energy> findByType(EnergyType type);
}
