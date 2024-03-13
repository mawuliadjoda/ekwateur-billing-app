package com.ekwateur.billing.consumption;

import com.ekwateur.billing.customer.entity.Customer;
import com.ekwateur.billing.energy.Energy;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "consumption")
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "energy_id")
    private Energy energy;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "total")
    private Double total;
}
