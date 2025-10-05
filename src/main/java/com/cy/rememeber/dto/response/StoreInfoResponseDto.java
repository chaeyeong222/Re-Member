package com.cy.rememeber.dto.response;

import com.cy.rememeber.Entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

//public class StoreInfoResponseDto {
//    private Long storeKey;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_key", nullable = false)
//    private User user;//
//
//    @Column(nullable = false)
//    private String storeName;
//
//    private String address;
//    private String introduction; // 가게 소개
//}
