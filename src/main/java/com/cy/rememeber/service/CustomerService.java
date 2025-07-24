package com.cy.rememeber.service;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.dto.response.FindCustomerDto;
import com.cy.rememeber.repository.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

//    public List<Customer> getCustomers(){
//        return customerRepository.findAll();
//    }

    /**
     * 고객 조회 - 이름
     * */
    public List<FindCustomerDto> getCustomer(String customerName){
        return customerRepository.findByName(customerName);
    }

    /**
     * 고객 조회 - 번호
     * */
    public FindCustomerDto getPhone(String customerPhone){
//        return customerRepository.findByPhone(customerPhone).orElse(null);
        return customerRepository.findByPhone(customerPhone);
    }

}
