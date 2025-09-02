package com.cy.rememeber.service;
import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.Entity.User;
import com.cy.rememeber.dto.UserSignUpDto;
import com.cy.rememeber.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 새로운 유저를 등록하는 메서드
     * 기본 역할은 CUSTOMER로 설정
     */
    public void registerNewUser(UserSignUpDto userSignUpDto){
        Optional<User> existingUser = userRepository.findBySocialId(userSignUpDto.getSocialId());
        if(existingUser.isPresent()){ //이미 등록된 유저인지 확인
            throw new IllegalStateException("User with social ID " + userSignUpDto.getSocialId() + " already exists.");
        }
        User newUser = User.builder()
                .socialId(userSignUpDto.getSocialId())
                .userName(userSignUpDto.getName())
                .phone(userSignUpDto.getPhone())
                .role(User.Role.CUSTOMER)
                .build();
        userRepository.save(newUser);
    }

    /**
     * 유저의 소셜 ID로 유저 정보를 가져오는 메서드
     * @param socialId 카카오 소셜 ID
     * @return User
     */
    public User getUser(String socialId){
        return userRepository.findBySocialId(socialId).orElse(null);
    }

    /**
     * 유저의 역할을 STORE_OWNER로 업데이트하는 메서드
     * @param socialId 유저 소셜 ID
     */
    public void updateUserRole(String socialId){
        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(()->new IllegalStateException("User not foudn with social ID: " + socialId));
        user.setRole(User.Role.OWNER);
        userRepository.save(user);
    }




}
