package com.cy.rememeber.service;

import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.dto.StoreSignDto;
import com.cy.rememeber.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

//    private final PasswordEncoder passwordEncoder;
    private final StoreRepository storeRepository;

    /**
    * 회원가입, STORE 생성
    * */
    public boolean signUp(StoreSignDto storeSignDto) throws Exception{
        if(storeRepository.findById(storeSignDto.getId()).isPresent()){
//            throw new Exception("이미 존재하는 아이디입니다.");
            return false;
        }

        Store store = Store.builder()
            .id(storeSignDto.getId())
            .name(storeSignDto.getName())
            .email(storeSignDto.getEmail())
            .password(storeSignDto.getPassword())
            .build();
//        store.passwordEncode(passwordEncoder);
        storeRepository.save(store);
        return true;
    }

    public Store getStore(String storeId){
        return storeRepository.findById(storeId).orElse(null);
    }

}
