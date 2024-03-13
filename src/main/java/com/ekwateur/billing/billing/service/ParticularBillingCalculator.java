package com.ekwateur.billing.billing.service;

import com.ekwateur.billing.billing.model.ParticularBillingRequestModel;
import com.ekwateur.billing.billing.model.ParticularBillingResponseModel;
import com.ekwateur.billing.consumption.Consumption;
import com.ekwateur.billing.consumption.repository.ConsumptionRepository;
import com.ekwateur.billing.customer.dto.CustomerParMapper;
import com.ekwateur.billing.customer.entity.CustomerPar;
import com.ekwateur.billing.customer.exception.CustomerNotFoundException;
import com.ekwateur.billing.customer.exception.InvalidReferenceException;
import com.ekwateur.billing.customer.repository.CustomerParRepository;
import com.ekwateur.billing.energy.EnergyType;
import com.ekwateur.billing.pricing.config.PricingProperties;
import com.ekwateur.billing.validator.CustomerReferenceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ParticularBillingCalculator implements Function<ParticularBillingRequestModel, ParticularBillingResponseModel> {

    private final PricingProperties pricingProperties;
    private final CustomerReferenceValidator customerReferenceValidator;
    private final CustomerParRepository customerParRepository;

    private final CustomerParMapper customerParMapper;

    private final ConsumptionRepository consumptionRepository;

    @Override
    public ParticularBillingResponseModel apply(ParticularBillingRequestModel particularBillingRequestModel) {

        boolean isValidReference = customerReferenceValidator.test(particularBillingRequestModel.reference());

        if(!isValidReference) {
            // String message = StringTemplate.STR."Customer reference \{particularBillingRequestModel.reference()} is not valid";
            StringJoiner message = new StringJoiner("");
            message.add("Customer reference")
                    .add(particularBillingRequestModel.reference())
                    .add("is not valid");
            throw new InvalidReferenceException(message.toString());
        }

        CustomerPar customerPar = customerParRepository.findByReference(particularBillingRequestModel.reference()).orElseThrow(CustomerNotFoundException::new);


        // double electricityBilling = particularBillingRequestModel.electricityConsumption() * pricingProperties.getElectricity().getParticular();
        // double gazBilling = particularBillingRequestModel.gazConsumption() * pricingProperties.getGaz().getParticular();

        Optional<Consumption> electricityConsumptionOptional = consumptionRepository.findConsumption(
                particularBillingRequestModel.reference(),
                EnergyType.ELECTRICITY,
                particularBillingRequestModel.year(),
                particularBillingRequestModel.month()
        );

        Optional<Consumption> gazConsumptionOptional = consumptionRepository.findConsumption(
                particularBillingRequestModel.reference(),
                EnergyType.GAZ,
                particularBillingRequestModel.year(),
                particularBillingRequestModel.month()
        );

        // TODO
        double electricityConsumption = electricityConsumptionOptional.isPresent() ? electricityConsumptionOptional.get().getTotal() : 0.0;

        double gazConsumption = gazConsumptionOptional.isPresent() ? gazConsumptionOptional.get().getTotal(): 0.0;

        double electricityBilling = electricityConsumption * pricingProperties.getElectricity().getParticular();
        double gazBilling = gazConsumption * pricingProperties.getGaz().getParticular();

        ParticularBillingResponseModel responseModel = new ParticularBillingResponseModel(
                particularBillingRequestModel.reference(),
                electricityBilling + gazBilling,
                customerParMapper.mapToDTO(customerPar)
        );

        return responseModel;
    }
}
