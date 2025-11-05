package com.cy.rememeber.controller;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.Entity.User;
import com.cy.rememeber.Entity.VisitHistory;
import com.cy.rememeber.dto.request.AddCustomerRequestDto;
import com.cy.rememeber.dto.request.AddVisitHistoryRequestDto;
import com.cy.rememeber.dto.response.FindCustomerDto;
import com.cy.rememeber.service.CustomerService;
import java.util.List;

import com.cy.rememeber.service.VisitHistoryService;
import java.util.Optional;
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

    @PostMapping("/{id}/editCustomerHistory")
    public ResponseEntity<?> addCustomerHistory(
            @PathVariable("id") Long customerKey,
            @RequestBody AddVisitHistoryRequestDto dto) {

        // PathVariable과 RequestBody의 customerKey가 일치하도록 설정
        dto.setCustomerKey(customerKey);

        VisitHistory savedHistory = visitHistoryService.addVisitHistory(dto);
        return new ResponseEntity<>(savedHistory, HttpStatus.CREATED);
    }

    // customerKey(Long)로 고객 정보 조회 (프론트엔드에서 사용)
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByCustomerKey(@PathVariable("id") Long customerKey) {
        FindCustomerDto customer = customerService.getUserByCustomerKey(customerKey);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody AddCustomerRequestDto dto) {
        Customer savedCustomer = customerService.addCustomer(dto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
}
