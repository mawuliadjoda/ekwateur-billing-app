package com.ekwateur.billing.customer.controller;

import com.ekwateur.billing.customer.dto.CustomerProDTO;
import com.ekwateur.billing.customer.service.CustomerProService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer-pros")
@RequiredArgsConstructor
public class CustomerProController {

    private final CustomerProService customerProService;

    @GetMapping
    List<CustomerProDTO> findAll() {
        return customerProService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CustomerProDTO create(@RequestBody @Validated CustomerProDTO customerProDTO) {
        return customerProService.save(customerProDTO);
    }
}
