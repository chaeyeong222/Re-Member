package com.cy.rememeber.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@SuperBuilder
@Table(name = "Store")
@NoArgsConstructor
@Getter
@Setter
public class Store {
    @Id
    @Column
    private long storeKey;
    private String id;
    private String password;
    private String email;
    private String name;

    //비밀번호 암호화 메서드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

}
