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
    private Long customerKey;
    private String customerName;
    private String customerPhone;
    private int visitCnt;
    private String memo;
}




