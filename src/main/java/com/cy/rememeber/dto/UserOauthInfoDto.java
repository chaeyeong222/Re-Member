package com.cy.rememeber.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserOauthInfoDto {
    String social_id;// 카카오 유저 아이디
    String nickname;
    boolean isUser; //기존회원여부

    String phoneNumber;
    String name;
}
