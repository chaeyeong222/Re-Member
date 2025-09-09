package com.cy.rememeber.business.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FindCustomerDto {
    private Long customerKey;
    private String customerName;
    private String customerPhone;
    private int visitCnt;
    private String memo;
    private String lastVisit;
    private String joinDate; // ���ʹ湮

}
