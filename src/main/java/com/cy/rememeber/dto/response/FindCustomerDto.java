package com.cy.rememeber.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FindCustomerDto {
    String customerName;
    String customerPhone;
    int visitCnt;
    String memo;
}
