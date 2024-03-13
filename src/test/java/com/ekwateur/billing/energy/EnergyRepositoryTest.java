package com.ekwateur.billing.energy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EnergyRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    EnergyRepository energyRepository;

    @BeforeEach
    void setUp() {
        Energy energy = new Energy();
        energy.setType(EnergyType.ELECTRICITY);

        energyRepository.save(energy);
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }


    @Test
    void shouldFindByType() {
        // Given
        EnergyType type = EnergyType.ELECTRICITY;

        Optional<Energy> energy = energyRepository.findByType(type);

        assertThat(energy.isPresent());
        assertThat(energy.get().getType()).isEqualTo(type);
    }


}