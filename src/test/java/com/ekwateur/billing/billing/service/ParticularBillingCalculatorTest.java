package com.ekwateur.billing.billing.service;

import com.ekwateur.billing.billing.model.ParticularBillingRequestModel;
import com.ekwateur.billing.billing.model.ParticularBillingResponseModel;
import com.ekwateur.billing.consumption.Consumption;
import com.ekwateur.billing.consumption.repository.ConsumptionRepository;
import com.ekwateur.billing.customer.dto.CustomerParDTO;
import com.ekwateur.billing.customer.dto.CustomerParMapper;
import com.ekwateur.billing.customer.entity.Civility;
import com.ekwateur.billing.customer.entity.CustomerPar;
import com.ekwateur.billing.customer.entity.CustomerType;
import com.ekwateur.billing.customer.exception.CustomerNotFoundException;
import com.ekwateur.billing.customer.exception.InvalidReferenceException;
import com.ekwateur.billing.customer.repository.CustomerParRepository;
import com.ekwateur.billing.energy.Energy;
import com.ekwateur.billing.energy.EnergyType;
import com.ekwateur.billing.pricing.config.PricingProperties;
import com.ekwateur.billing.validator.CustomerReferenceValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ParticularBillingCalculatorTest {
    private ParticularBillingCalculator particularBillingCalculator;


    private PricingProperties pricingProperties;
    private CustomerReferenceValidator customerReferenceValidator;
    private CustomerParRepository customerParRepository;
    private CustomerParMapper customerParMapper;

    private ConsumptionRepository consumptionRepository;

    PricingProperties.Electricity electricityPricing;
    PricingProperties.Gaz gazPricing;

    CustomerPar customerPar;
    CustomerParDTO customerParDTO;

    @BeforeEach
    void setUp() {
        pricingProperties = mock(PricingProperties.class);
        customerReferenceValidator = mock(CustomerReferenceValidator.class);
        customerParRepository = mock(CustomerParRepository.class);
        customerParMapper = mock(CustomerParMapper.class);
        consumptionRepository = mock(ConsumptionRepository.class);
        particularBillingCalculator = new ParticularBillingCalculator(
                pricingProperties,
                customerReferenceValidator,
                customerParRepository,
                customerParMapper,
                consumptionRepository
        );


        electricityPricing = new PricingProperties.Electricity();
        PricingProperties.Electricity.Professional professionalElectricityPricing = new PricingProperties.Electricity.Professional();
        professionalElectricityPricing.setGt1M(0.114);
        professionalElectricityPricing.setLt1M(0.118);

        electricityPricing.setParticular(0.21);
        electricityPricing.setProfessional(professionalElectricityPricing);

        gazPricing = new PricingProperties.Gaz();
        PricingProperties.Gaz.Professional professionalGazPricing = new PricingProperties.Gaz.Professional();
        professionalGazPricing.setGt1M(0.111);
        professionalGazPricing.setLt1M(0.113);

        gazPricing.setParticular(0.115);
        gazPricing.setProfessional(professionalGazPricing);

        customerPar = new CustomerPar();
        customerPar.setReference("EKW12345678");
        customerPar.setType(CustomerType.PAR);
        customerPar.setCivility(Civility.MONSIEUR);
        customerPar.setLastName("ADJODA");
        customerPar.setFirstName("Mawuli");

        customerParDTO = new CustomerParDTO("EKW12345678", CustomerType.PAR, Civility.MONSIEUR, "ADJODA", "Mawuli");


    }

    @Test
    void shouldCalculateBillingWhenGivenValidCustomerReference() {
        // Given
        var particularBillingRequestModel = new ParticularBillingRequestModel(
                "EKW12345678",
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue()
                // , 100, 100
        );
        var expectedResponse = new ParticularBillingResponseModel("EKW12345678", 32.5, customerParDTO);

        given(customerReferenceValidator.test(particularBillingRequestModel.reference())).willReturn(true);
        given(pricingProperties.getElectricity()).willReturn(electricityPricing);
        given(pricingProperties.getGaz()).willReturn(gazPricing);

        given(customerParRepository.findByReference(particularBillingRequestModel.reference())).willReturn(Optional.of(customerPar));
        given(customerParMapper.mapToDTO(customerPar)).willReturn(customerParDTO);


        Consumption electricityConsumption = new Consumption();
        electricityConsumption.setCustomer(new CustomerPar());
        electricityConsumption.setEnergy(new Energy(EnergyType.ELECTRICITY));
        electricityConsumption.setRegisteredAt(LocalDateTime.now());
        electricityConsumption.setTotal(100.0);

        Consumption gazConsumption = new Consumption();
        gazConsumption.setCustomer(new CustomerPar());
        gazConsumption.setEnergy(new Energy(EnergyType.GAZ));
        gazConsumption.setRegisteredAt(LocalDateTime.now());
        gazConsumption.setTotal(100.0);

        given(consumptionRepository.findConsumption(
                particularBillingRequestModel.reference(),
                EnergyType.GAZ,
                particularBillingRequestModel.year(),
                particularBillingRequestModel.month())
        ).willReturn(Optional.of(electricityConsumption));

        given(consumptionRepository.findConsumption(
                particularBillingRequestModel.reference(),
                EnergyType.ELECTRICITY,
                particularBillingRequestModel.year(),
                particularBillingRequestModel.month())
        ).willReturn(Optional.of(gazConsumption));


        // When
        ParticularBillingResponseModel result = particularBillingCalculator.apply(particularBillingRequestModel);

        // Then
        assertBillingResponse(expectedResponse, result);
    }

    @Test
    void shouldNotCalculateBillingWhenGivenInValidCustomerReference() {
        // Given
        var particularBillingRequestModel = new ParticularBillingRequestModel(
                "TYUIO12345678",
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue()
                // , 100, 100
        );


        given(customerReferenceValidator.test(particularBillingRequestModel.reference())).willThrow(InvalidReferenceException.class);

        assertThatThrownBy(() -> particularBillingCalculator.apply(particularBillingRequestModel)).isInstanceOf(InvalidReferenceException.class);
    }

    @Test
    void shouldNotCalculateBillingWhenGivenNonExistingCustomerReference() {
        // Given
        var particularBillingRequestModel = new ParticularBillingRequestModel(
                "EKW12345600",
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue()
                // , 100, 100
        );

        given(customerReferenceValidator.test(particularBillingRequestModel.reference())).willReturn(true);
        given(customerParRepository.findByReference(particularBillingRequestModel.reference())).willThrow(CustomerNotFoundException.class);


        assertThatThrownBy(() -> particularBillingCalculator.apply(particularBillingRequestModel)).isInstanceOf(CustomerNotFoundException.class);
    }

    private void assertBillingResponse(ParticularBillingResponseModel expected, ParticularBillingResponseModel actual) {
        assertThat(expected.reference()).isEqualTo(actual.reference());
        assertThat(expected.grandTotal()).isEqualTo(actual.grandTotal());
    }
}