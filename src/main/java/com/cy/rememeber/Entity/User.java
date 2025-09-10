package com.cy.rememeber.Entity;

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
    private String userName;
    private String phone;
    private String email;
    private String nickname; //카카오 닉네임 임시전달용
    private boolean isUser;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
        CUSTOMER, OWNER
    }
}

