package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findByName(String customerName);
    Optional<Customer> findByPhone(String customerPhone);
}
