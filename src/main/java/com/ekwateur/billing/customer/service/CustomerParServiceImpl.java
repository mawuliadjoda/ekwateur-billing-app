package com.ekwateur.billing.customer.service;

import com.ekwateur.billing.customer.dto.CustomerParDTO;
import com.ekwateur.billing.customer.dto.CustomerParMapper;
import com.ekwateur.billing.customer.entity.CustomerPar;
import com.ekwateur.billing.customer.repository.CustomerParRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerParServiceImpl implements CustomerParService {

    private final CustomerParRepository customerParRepository;
    private final CustomerParMapper customerParMapper;


    @Override
    public List<CustomerParDTO> findAll() {
        return customerParMapper.mapToDTOS(customerParRepository.findAll());
    }

    @Override
    public CustomerParDTO save(CustomerParDTO customerProDTO) {
        CustomerPar customerPar = customerParMapper.mapToEntity(customerProDTO);
        CustomerPar saved = customerParRepository.save(customerPar);
        return customerParMapper.mapToDTO(saved);
    }

    @Override
    public CustomerParDTO findById(Long id) {
        return null;
    }

    @Override
    public CustomerParDTO update(CustomerParDTO customerParDTO, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
