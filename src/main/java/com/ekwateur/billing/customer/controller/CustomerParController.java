package com.ekwateur.billing.customer.controller;

import com.ekwateur.billing.customer.dto.CustomerParDTO;
import com.ekwateur.billing.customer.service.CustomerParService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer-pars")
@RequiredArgsConstructor
public class CustomerParController {
    private final CustomerParService customerParService;

    @GetMapping
    List<CustomerParDTO> findAll() {
        return customerParService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CustomerParDTO create(@RequestBody @Validated CustomerParDTO customerParDTO) {
        return customerParService.save(customerParDTO);
    }
}
