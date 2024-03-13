package com.ekwateur.billing.customer.entity;


import com.ekwateur.billing.consumption.Consumption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// @Table(name = "customer")
public abstract class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 11)
    private String reference;

    @Enumerated(value = EnumType.STRING)
    private CustomerType type;

    @OneToMany(mappedBy = "customer")
    private Set<Consumption> consumptions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
