package com.ekwateur.billing.customer.service;

import com.ekwateur.billing.customer.dto.CustomerProDTO;
import com.ekwateur.billing.customer.dto.CustomerProMapper;
import com.ekwateur.billing.customer.entity.CustomerPro;
import com.ekwateur.billing.customer.repository.CustomerProRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerProServiceImpl implements CustomerProService {
    private final CustomerProRepository customerProRepository;
    private final CustomerProMapper customerProMapper;
    @Override
    public List<CustomerProDTO> findAll() {
        return customerProMapper.mapToDTOS(customerProRepository.findAll());
    }

    @Override
    public CustomerProDTO save(CustomerProDTO customerProDTO) {
        CustomerPro customerPro = customerProMapper.mapToEntity(customerProDTO);
        CustomerPro saved = customerProRepository.save(customerPro);
        return customerProMapper.mapToDTO(saved);
    }

    @Override
    public CustomerProDTO findById(Long id) {
        return null;
    }

    @Override
    public CustomerProDTO update(CustomerProDTO customerParDTO, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
