package com.ekwateur.billing.energy.dto;

import com.ekwateur.billing.energy.Energy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EnergyMapper {
    Energy mapToEntity(EnergyDTO energyDTO);

    EnergyDTO mapToDTO(Energy energy);

    List<Energy> mapToEntities(List<EnergyDTO> energyDTOS);

    List<EnergyDTO> mapToDTOS(List<Energy> energies);
}
