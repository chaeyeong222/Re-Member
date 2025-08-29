package com.cy.rememeber.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Entity
@SuperBuilder
@Table(name = "Customer")
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@DynamicInsert
public class Customer {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerKey; //고객키
    private String customerName; //고객명
    private String customerPhone; //휴대폰번호
    private int visitCnt; //방문횟수
    private String memo; //메모
    private Timestamp lastVisit; //최근방문
    private Timestamp joinDate; //최초방문

    private Long storeKey;
    //TODO : tag 기능
}




