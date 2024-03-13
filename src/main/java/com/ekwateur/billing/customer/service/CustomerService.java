package com.ekwateur.billing.customer.service;

import java.util.List;

public interface CustomerService<T> {
    List<T> findAll();

    T save(T customerProDTO);

    T findById(Long id);

    T update(T customerParDTO, Long id);

    void deleteById(Long id);

}
