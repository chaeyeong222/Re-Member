package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.dto.response.FindCustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<FindCustomerDto> findByUser_UserNameContaining(String userName);

    List<FindCustomerDto> findByUser_PhoneContaining(String phone);

    List<FindCustomerDto> findByStore_StoreKey(Long storeKey);

    List<FindCustomerDto> findCustomersByStoreStoreKey(Long storeKey);
}