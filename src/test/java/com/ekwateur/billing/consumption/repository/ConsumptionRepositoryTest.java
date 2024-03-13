package com.ekwateur.billing.consumption.repository;

import com.ekwateur.billing.consumption.Consumption;
import com.ekwateur.billing.customer.entity.Civility;
import com.ekwateur.billing.customer.entity.CustomerPar;
import com.ekwateur.billing.customer.entity.CustomerType;
import com.ekwateur.billing.customer.repository.CustomerParRepository;
import com.ekwateur.billing.energy.Energy;
import com.ekwateur.billing.energy.EnergyRepository;
import com.ekwateur.billing.energy.EnergyType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConsumptionRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    ConsumptionRepository consumptionRepository;

    @Autowired
    EnergyRepository energyRepository;

    @Autowired
    CustomerParRepository customerParRepository;

    CustomerPar customerPar;
    Energy energy;

    @BeforeEach
    void setUp() {

        customerPar = new CustomerPar();
        customerPar.setReference("EWK12345678");
        customerPar.setType(CustomerType.PAR);
        customerPar.setCivility(Civility.MONSIEUR);
        customerPar.setLastName("ADJODA");
        customerPar.setFirstName("Mawuli");
        customerPar = customerParRepository.save(customerPar);

        energy = new Energy();
        energy.setType(EnergyType.ELECTRICITY);
        energy = energyRepository.save(energy);

        Consumption consumption = new Consumption();
        consumption.setEnergy(energy);
        consumption.setCustomer(customerPar);
        consumption.setTotal(10.0);

        consumption.setRegisteredAt(LocalDateTime.now());

        consumption = consumptionRepository.save(consumption);

    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }


    @Test
    void shouldFindConsumption() {


        Optional<Consumption> consumption = consumptionRepository.findConsumption(
                customerPar.getReference(),
                energy.getType(),
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue());

        assertThat(consumption.isPresent());
    }
}