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
    private final StoreService storeService;

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody UserSignUpDto userSignUpDto, HttpServletRequest request) throws Exception {
//        userSignUpDto.setToken(jwtService.extractAccessToken(request).orElse(null));
        //db에 있는지 체크
        User user = userService.getUser(userSignUpDto.getId());
        if(user!=null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/signup") // 회원가입을 위한 별도의 URL 사용
    public ResponseEntity<?> createUser(@RequestBody UserSignUpDto userSignUpDto) {
        try {
            // 회원가입 로직
            Store newStore = storeService.registerNewUser(userSignUpDto);
            log.info("New user registered successfully. Social ID: {}", newStore.getSocialId());

            // TODO: 회원가입 성공 후 JWT 토큰 발급 및 반환
            // String jwtToken = jwtService.createToken(newStore.getSocialId());
            // return ResponseEntity.ok(Map.of("message", "Signup successful", "token", jwtToken));

            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            log.error("User registration failed.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }




}
