package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.dto.response.FindCustomerDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

//@Transactional
//public interface CustomerRepository extends JpaRepository<Customer, Long> {
//    Optional<Customer> findByName(String customerName);
//    Optional<Customer> findByPhone(String customerPhone);
//    @Query(value = "select customer_name, customer_phone, visit_cnt, memo from customer where customer_phone=?", nativeQuery = true)
//    FindCustomerDto findByPhone(String customerPhone);
//
//    @Query(value = "select customer_name, customer_phone, visit_cnt, memo from customer where customer_name=?", nativeQuery = true)
//    List<FindCustomerDto> findByName(String customer_name);
//}
