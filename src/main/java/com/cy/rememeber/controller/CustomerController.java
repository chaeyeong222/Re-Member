package com.cy.rememeber.controller;

import com.cy.rememeber.Entity.VisitHistory;
import com.cy.rememeber.dto.response.FindCustomerDto;
import com.cy.rememeber.service.CustomerService;
import java.util.List;

import com.cy.rememeber.service.VisitHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final VisitHistoryService visitHistoryService;

    @GetMapping("/findByPhone")
    public ResponseEntity<?> findByPhone(@RequestParam("customerPhone") String customerPhone) throws Exception{
        List<FindCustomerDto> customerList = customerService.getPhone(customerPhone);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ResponseEntity<?> findByName(@RequestParam("customerName") String customerName) throws Exception{
        List<FindCustomerDto> customerList = customerService.getCustomer(customerName);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<?> getCustomersList(@RequestParam("storeKey") Long storeKey) throws Exception{
        List<FindCustomerDto> customerList = customerService.getAllCustomers(storeKey);

        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping("/{id}/customerHistory")
    public ResponseEntity<?> getCustomersHistory(@PathVariable("id") Long customerKey) throws Exception{
        List<VisitHistory> histories = visitHistoryService.getVisitHistoryByCustomerKey(customerKey);
        if (histories == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }

}
