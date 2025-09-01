package com.cy.rememeber.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserSignUpDto {
    private String email;
//    private String phone;
    private String id;
    private String nickname;
    private String token;
}
