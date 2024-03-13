package com.ekwateur.billing.customer.repository;

import com.ekwateur.billing.customer.entity.CustomerPar;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerParRepository extends CustomerRepository<CustomerPar> {
    Optional<CustomerPar> findByReference(String reference);
}
