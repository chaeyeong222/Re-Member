package com.cy.rememeber.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Entity
@SuperBuilder
@Table(name = "Store")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@DynamicInsert
public class Store {

    @Id
    @Column(name = "store_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeKey;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key", nullable = false)
    private User user;//

    @Column(nullable = false)
    private String storeName;

    private String phone;
    private String address;
    private String introduction; // 가게 소개

//    private String password;
//    private String email;
//    @JsonIgnore
//    @OneToMany
//    private List<Customer> customerList;

    //비밀번호 암호화 메서드
//    public void passwordEncode(PasswordEncoder passwordEncoder) {
//        this.password = passwordEncoder.encode(this.password);
//    }

}
