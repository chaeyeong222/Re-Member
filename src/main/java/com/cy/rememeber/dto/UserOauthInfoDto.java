package com.cy.rememeber.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserOauthInfoDto {
    private String socialId;// 카카오 유저 아이디
    private String nickname;
    private boolean isUser; //기존회원여부
    private String phoneNumber;
    private String name;

}
