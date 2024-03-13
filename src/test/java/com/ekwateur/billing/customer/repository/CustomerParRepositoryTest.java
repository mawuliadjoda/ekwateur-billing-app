package com.ekwateur.billing.customer.repository;

import com.ekwateur.billing.customer.entity.Civility;
import com.ekwateur.billing.customer.entity.CustomerPar;
import com.ekwateur.billing.customer.entity.CustomerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerParRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    CustomerParRepository customerParRepository;

    @BeforeEach
    void setUp() {
        CustomerPar customerPar = new CustomerPar();
        customerPar.setReference("EKW12345678");
        customerPar.setType(CustomerType.PAR);
        customerPar.setCivility(Civility.MONSIEUR);
        customerPar.setLastName("ADJODA");
        customerPar.setFirstName("Mawuli");

        customerParRepository.save(customerPar);
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void shouldFindCustomerByReference() {
        // Given
        var reference = "EKW12345678";

        // When
        Optional<CustomerPar> customerPar = customerParRepository.findByReference(reference);

        // Then
        assertThat(customerPar.isPresent());
        assertThat(customerPar.get().getReference()).isEqualTo(reference);
    }
}