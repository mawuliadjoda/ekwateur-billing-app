package com.ekwateur.billing.customer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "customerPar")
public class CustomerPar extends Customer {
    @Enumerated(value = EnumType.STRING)
    private Civility civility;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;
}
