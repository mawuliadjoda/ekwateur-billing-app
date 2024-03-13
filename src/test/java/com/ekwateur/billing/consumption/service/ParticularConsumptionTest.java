package com.ekwateur.billing.consumption.service;

import com.ekwateur.billing.consumption.Consumption;
import com.ekwateur.billing.consumption.repository.ConsumptionRepository;
import com.ekwateur.billing.consumption.controller.ConsumptionRequestModel;
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
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

class ParticularConsumptionTest {

    ParticularConsumption particularConsumption;

    private CustomerParRepository customerParRepository;
    private EnergyRepository energyRepository;
    private ConsumptionRepository consumptionRepository;

    CustomerPar customerPar;
    @BeforeEach
    void setUp() {
        customerParRepository = mock(CustomerParRepository.class);
        energyRepository = mock(EnergyRepository.class);
        consumptionRepository = mock(ConsumptionRepository.class);

        particularConsumption = new ParticularConsumption(customerParRepository, energyRepository, consumptionRepository );

        customerPar = new CustomerPar();
        customerPar.setReference("EWK12345678");
        customerPar.setType(CustomerType.PAR);
        customerPar.setCivility(Civility.MONSIEUR);
        customerPar.setLastName("ADJODA");
        customerPar.setFirstName("Mawuli");
    }

    @Test
    void shouldRegisterConsumption() {
        // Given
        ConsumptionRequestModel consumptionRequestModel = new ConsumptionRequestModel(
                "EWK12345678",
                EnergyType.ELECTRICITY,
                LocalDateTime.now(),
                10.0
        );

        Energy energy = new Energy();
        energy.setId(1L);
        energy.setType(EnergyType.ELECTRICITY);

        given(customerParRepository.findByReference(consumptionRequestModel.customerReference())).willReturn(Optional.of(customerPar));
        given(energyRepository.findByType(consumptionRequestModel.energyType())).willReturn(Optional.of(energy));

        ArgumentCaptor<Consumption> consumptionArgumentCaptor = ArgumentCaptor.forClass(Consumption.class);

        // When
        particularConsumption.accept(consumptionRequestModel);
        // Then
        verify(consumptionRepository, times(1)).save(consumptionArgumentCaptor.capture());

        Assertions.assertThat(consumptionRequestModel.customerReference()).isEqualTo(consumptionArgumentCaptor.getValue().getCustomer().getReference());
    }
}