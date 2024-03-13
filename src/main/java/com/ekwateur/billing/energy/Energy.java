package com.ekwateur.billing.energy;

import com.ekwateur.billing.consumption.Consumption;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "energy")
public class Energy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private EnergyType type;

    @OneToMany(mappedBy = "energy")
    private Set<Consumption> consumptions = new HashSet<>();

    public Energy(EnergyType type) {
        this.type = type;
    }
}
