package com.cy.rememeber.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "Customer")
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @Column
    private long customerKey;
    private String customerName;
    private String customerPhone;
    private int visitCnt;
    private String memo;

}
