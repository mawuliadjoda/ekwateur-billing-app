package com.ekwateur.billing.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "customerPro")
public class CustomerPro extends Customer {
    @Column(name = "siret")
    private String siret;
    @Column(name = "social-reason")
    private String socialReason;

    @Column(name = "revenue")
    private Double revenue;
}
