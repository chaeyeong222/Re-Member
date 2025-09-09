package com.cy.rememeber.business.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User {

    @Id
    @Column(name = "user_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userKey;

    private String socialId;//카카오에서 받아온 고유 id
    private String name;
    private String phone;
    private String email;
    private boolean isUser;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
        GUEST, CUSTOMER, OWNER
    }

    // 유저 권한 승격 메소드
    public void authorizeUser() {
        this.role = Role.CUSTOMER;
    }

    // 비밀번호 암호화 메소드
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}

