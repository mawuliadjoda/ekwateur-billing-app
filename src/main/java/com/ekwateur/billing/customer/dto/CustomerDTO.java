package com.ekwateur.billing.customer.dto;

import com.ekwateur.billing.customer.entity.CustomerType;
import lombok.*;

@Getter
@AllArgsConstructor
public sealed class CustomerDTO permits CustomerParDTO, CustomerProDTO {
    private String reference;
    private CustomerType type;
}
