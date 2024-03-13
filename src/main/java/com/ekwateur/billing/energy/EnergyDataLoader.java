package com.ekwateur.billing.energy;

import com.ekwateur.billing.energy.dto.Energies;
import com.ekwateur.billing.pricing.config.PricingProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnergyDataLoader implements CommandLineRunner {


    private final EnergyRepository energyRepository;
    private final ObjectMapper objectMapper;
    private final PricingProperties pricingProperties;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(pricingProperties);
        // Electricity electricity = pricingConfiguration.getElectricity();
        // Gaz gaz = pricingConfiguration.getGaz();
        if (energyRepository.count() == 0) {
            String energyJson = "/data/energies.json";

            log.info("Loading energies into database from JSON: {}", energyJson);

            try {
                InputStream inputStream = TypeReference.class.getResourceAsStream(energyJson);
                Energies response = objectMapper.readValue(inputStream, Energies.class);
                energyRepository.saveAll(response.energies());
            } catch (Exception e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }
}
