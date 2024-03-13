package com.ekwateur.billing.customer.dto;

import com.ekwateur.billing.customer.entity.Civility;
import com.ekwateur.billing.customer.entity.CustomerType;
import lombok.*;

@Getter
public non-sealed class CustomerParDTO extends CustomerDTO {
    private Civility civility;
    private String lastName;
    private String firstName;

    @Builder
    public CustomerParDTO(String reference, CustomerType type, Civility civility, String lastName, String firstName) {
        super(reference, type);
        this.civility = civility;
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
