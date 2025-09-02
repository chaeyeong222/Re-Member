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
     * ���ο� ������ ����ϴ� �޼���
     * �⺻ ������ CUSTOMER�� ����
     */
    public void registerNewUser(UserSignUpDto userSignUpDto){
        Optional<User> existingUser = userRepository.findBySocialId(userSignUpDto.getSocialId());
        if(existingUser.isPresent()){ //�̹� ��ϵ� �������� Ȯ��
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
     * ������ �Ҽ� ID�� ���� ������ �������� �޼���
     * @param socialId īī�� �Ҽ� ID
     * @return User
     */
    public User getUser(String socialId){
        return userRepository.findBySocialId(socialId).orElse(null);
    }

    /**
     * ������ ������ STORE_OWNER�� ������Ʈ�ϴ� �޼���
     * @param socialId ���� �Ҽ� ID
     */
    public void updateUserRole(String socialId){
        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(()->new IllegalStateException("User not foudn with social ID: " + socialId));
        user.setRole(User.Role.OWNER);
        userRepository.save(user);
    }




}
