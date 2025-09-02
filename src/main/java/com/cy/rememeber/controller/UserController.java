package com.cy.rememeber.controller;

import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.Entity.User;
import com.cy.rememeber.dto.UserSignUpDto;
import com.cy.rememeber.service.StoreService;
import com.cy.rememeber.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring6.processor.SpringUErrorsTagProcessor;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/signup/customer") // 회원가입을 위한 별도의 URL 사용
    public ResponseEntity<?> registerCustomer(@RequestBody UserSignUpDto userSignUpDto) {
        try {
            // 회원가입 로직
            userService.registerNewUser(userSignUpDto);
            log.info("New customer user registered successfully with social ID: {}", userSignUpDto.getSocialId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer user created successfully");
        } catch (Exception e) {
            log.error("Customer registration failed.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/{socialId}")
    public ResponseEntity<?> getUser(@PathVariable String socialId){
        User user = userService.getUser(socialId);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
