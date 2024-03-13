package com.ekwateur.billing.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerReferenceValidatorTest {

    @InjectMocks
    CustomerReferenceValidator customerReferenceValidator;


    @ParameterizedTest
    @CsvSource(value = {
            "EKW12345678, true",
            "EKW12345678TYUIO, false",
            "12345678TYUIO, false",
    })
    void shouldValidateCustomerReference(String reference, boolean expected) {
        // Given
        // var reference = "EKW12345678";

        // When
        boolean result = customerReferenceValidator.test(reference);

        // Then
        Assertions.assertThat(result).isEqualTo(expected);

    }
}