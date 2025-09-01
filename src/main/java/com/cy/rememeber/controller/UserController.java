package com.cy.rememeber.controller;

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


}
