package com.cy.rememeber.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StoreSignDto {
    private String storeName;
    private String address;
    private String email;
    private String introduction;
    private String socialId;
}
