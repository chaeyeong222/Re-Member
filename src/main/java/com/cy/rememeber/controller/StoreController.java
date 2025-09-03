package com.cy.rememeber.controller;

import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.dto.StoreSignDto;
import com.cy.rememeber.service.StoreService;
import com.cy.rememeber.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;

    /**
     * 가게 등록 메서드.
     * 이 엔드포인트는 CUSTOMER 유저가 STORE_OWNER로 역할을 변경하고, 가게를 등록할 때 사용.
     * @RequestBody StoreSignDto에는 사용자의 socialId와 함께 가게 정보가 포함되어야 함.
     * */
    @PostMapping("/register")
    public ResponseEntity<?> registerStore(@RequestBody StoreSignDto storeSignDto){
        try {
            //유저 역할 update
            userService.updateUserRole(storeSignDto.getSocialId());

            storeService.registerStore(storeSignDto);
            log.info("Store registered successfully for user: {}", storeSignDto.getSocialId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Store registered successfully and user role updated.");
        }catch (Exception e){
            log.error("Store registration failed.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Store registration failed: " + e.getMessage());
        }
    }

    /**
     * 특정 가게정보를 가져오는 메서드
     * */
    @GetMapping("/{storeid}")
    public ResponseEntity<?> getStore(@PathVariable("socialId") String socialId){
        Store store = storeService.getStoreBySocialId(socialId);
        if(store==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(store, HttpStatus.OK);
    }



}
