package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.dto.response.FindCustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<FindCustomerDto> findByCustomerNameContaining(String userName);

    List<FindCustomerDto> findByCustomerPhoneContaining(String phone);

    List<Customer> findByStore_StoreKey(Long storeKey);

    Customer findByCustomerKey(Long customerKey);

//    List<FindCustomerDto> findCustomersByStoreStoreKey(Long storeKey);

}