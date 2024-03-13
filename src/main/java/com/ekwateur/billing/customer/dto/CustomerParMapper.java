package com.ekwateur.billing.customer.dto;

import com.ekwateur.billing.customer.entity.CustomerPar;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CustomerParMapper {
    CustomerPar mapToEntity(CustomerParDTO customerParDTO);

    CustomerParDTO mapToDTO(CustomerPar customerPar);

    List<CustomerPar> mapToEntities(List<CustomerParDTO> customerParDTOS);

    List<CustomerParDTO> mapToDTOS(List<CustomerPar> customerPars);
}
