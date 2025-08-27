package com.cy.rememeber.service;

import com.cy.rememeber.dto.response.FindCustomerDto;
import com.cy.rememeber.repository.QCustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final QCustomerRepository QcustomerRepository;

    public List<FindCustomerDto> getCustomers(){
        return QcustomerRepository.findAll();
    }

    /**
     * 고객 조회 - 이름
     * */
    public List<FindCustomerDto> getCustomer(String customerName){
        return QcustomerRepository.findCustomerByName(customerName);
    }

    /**
     * 고객 조회 - 번호
     * */
    public List<FindCustomerDto> getPhone(String customerPhone){
        return QcustomerRepository.findByPhoneNum(customerPhone);
    }

}
