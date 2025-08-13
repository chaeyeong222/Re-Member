package com.cy.rememeber.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
//import org.springframework.security.crypto.password.PasswordEncoder;

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
    @JsonIgnore
    @OneToMany
    private List<Customer> customerList;
    //비밀번호 암호화 메서드
//    public void passwordEncode(PasswordEncoder passwordEncoder) {
//        this.password = passwordEncoder.encode(this.password);
//    }

}
