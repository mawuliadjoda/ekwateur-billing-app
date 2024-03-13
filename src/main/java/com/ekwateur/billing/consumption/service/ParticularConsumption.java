package com.ekwateur.billing.consumption.service;

import com.ekwateur.billing.consumption.Consumption;
import com.ekwateur.billing.consumption.repository.ConsumptionRepository;
import com.ekwateur.billing.consumption.controller.ConsumptionRequestModel;
import com.ekwateur.billing.customer.entity.CustomerPar;
import com.ekwateur.billing.customer.exception.CustomerNotFoundException;
import com.ekwateur.billing.customer.repository.CustomerParRepository;
import com.ekwateur.billing.energy.Energy;
import com.ekwateur.billing.energy.EnergyNotFoundException;
import com.ekwateur.billing.energy.EnergyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ParticularConsumption implements Consumer<ConsumptionRequestModel> {
    private final CustomerParRepository customerParRepository;
    private final EnergyRepository energyRepository;
    private final ConsumptionRepository consumptionRepository;
    @Override
    public void accept(ConsumptionRequestModel model) {
        CustomerPar customerPar = customerParRepository.findByReference(model.customerReference()).orElseThrow(CustomerNotFoundException::new);
        Energy energy = energyRepository.findByType(model.energyType()).orElseThrow(EnergyNotFoundException::new);

        Consumption consumption = new Consumption();
        consumption.setCustomer(customerPar);
        consumption.setEnergy(energy);

        consumption.setRegisteredAt(model.registeredAt());
        consumption.setTotal(model.total());

        consumptionRepository.save(consumption);

    }
}
