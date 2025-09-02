package com.cy.rememeber.service;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.dto.response.CustomerDetailDto;
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

//    public List<FindCustomerDto> getCustomers(){
//        return customerRepository.findAll();
//    }

    /**
     * 고객 조회 - 이름
     * */
    public List<FindCustomerDto> getCustomer(String customerName){
        return customerRepository.findByUser_UserNameContaining(customerName);
    }

    /**
     * 고객 조회 - 번호
     * */
    public List<FindCustomerDto> getPhone(String customerPhone){
        return customerRepository.findByUser_PhoneContaining(customerPhone);
    }

    public List<FindCustomerDto> getAllCustomers(Long storeKey) {
        return customerRepository.findAll(storeKey);
    }





}
