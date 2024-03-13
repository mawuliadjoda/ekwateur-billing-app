package com.ekwateur.billing.energy;

import com.ekwateur.billing.energy.dto.EnergyDTO;

import java.util.List;

public interface EnergyService {
    List<EnergyDTO> findAll();

    EnergyDTO save(EnergyDTO energyDTO);

    EnergyDTO findById(Long id);
}
