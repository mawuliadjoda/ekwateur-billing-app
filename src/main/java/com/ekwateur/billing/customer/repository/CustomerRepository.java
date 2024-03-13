package com.ekwateur.billing.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomerRepository<T>  extends JpaRepository<T, Long> {
}
