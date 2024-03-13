package com.ekwateur.billing.billing.model;

import com.ekwateur.billing.customer.dto.CustomerParDTO;
import com.ekwateur.billing.customer.entity.CustomerPar;

public record ParticularBillingResponseModel(
        String reference,
        double grandTotal,

        CustomerParDTO customerParDTO
) {
}
