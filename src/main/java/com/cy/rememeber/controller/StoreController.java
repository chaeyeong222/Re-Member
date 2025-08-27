package com.cy.rememeber.controller;

import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.dto.StoreSignDto;
import com.cy.rememeber.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    /**
     * 사업자 등록 메서드
     * */
    @ManagedOperation(description = "사업자 등록 메서드")
    @PostMapping("signup")
    public ResponseEntity<?> createStore(@RequestBody StoreSignDto storeSignDto, HttpServletRequest request) throws Exception{
        Boolean signUpCheck = storeService.signUp(storeSignDto);
        return new ResponseEntity<>(signUpCheck, HttpStatus.OK);
    }

    @GetMapping("/{storeid}")
    public ResponseEntity<?> getStore(@PathVariable("storeId") String storeId){
        Store store = storeService.getStore(storeId);
        return new ResponseEntity<>(store, HttpStatus.OK);
    }



}
