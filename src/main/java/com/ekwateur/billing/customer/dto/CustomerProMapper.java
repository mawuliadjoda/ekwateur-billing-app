package com.ekwateur.billing.customer.dto;

import com.ekwateur.billing.customer.entity.CustomerPro;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CustomerProMapper {
    CustomerPro mapToEntity(CustomerProDTO customerProDTO);

    CustomerProDTO mapToDTO(CustomerPro customerPro);

    List<CustomerPro> mapToEntities(List<CustomerProDTO> customerProDTOS);

    List<CustomerProDTO> mapToDTOS(List<CustomerPro> customerPros);
}
