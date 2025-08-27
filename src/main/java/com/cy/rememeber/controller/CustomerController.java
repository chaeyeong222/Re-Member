package com.cy.rememeber.controller;

import com.cy.rememeber.dto.response.FindCustomerDto;
import com.cy.rememeber.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 고객관리
 * */
@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/findByPhone")
    public ResponseEntity<?> findByPhone(@RequestParam("customerPhone") String customerPhone) throws Exception{
        List<FindCustomerDto> customer = customerService.getPhone(customerPhone);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ResponseEntity<?> findByName(@RequestParam("customerName") String customerName) throws Exception{
        List<FindCustomerDto> customerList = customerService.getCustomer(customerName);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

}
