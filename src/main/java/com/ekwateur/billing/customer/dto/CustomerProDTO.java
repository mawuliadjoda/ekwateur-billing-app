package com.ekwateur.billing.customer.dto;

import com.ekwateur.billing.customer.entity.CustomerType;
import lombok.Builder;
import lombok.Getter;


@Getter
public non-sealed class CustomerProDTO extends CustomerDTO {
    private String siret;
    private String socialReason;
    private Double revenue;

    @Builder
    public CustomerProDTO(String reference, CustomerType type, String siret, String socialReason, Double revenue) {
        super(reference, type);
        this.siret = siret;
        this.socialReason = socialReason;
        this.revenue = revenue;
    }
}
