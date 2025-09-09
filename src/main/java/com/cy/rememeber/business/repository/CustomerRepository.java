package com.cy.rememeber.business.repository;

import com.cy.rememeber.business.Entity.Customer;
import com.cy.rememeber.business.dto.response.FindCustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<FindCustomerDto> findByUser_UserNameContaining(String userName);

    List<FindCustomerDto> findByUser_PhoneContaining(String phone);

    List<FindCustomerDto> findByStore_StoreKey(Long storeKey);

    List<FindCustomerDto> findCustomersByStoreStoreKey(Long storeKey);
}