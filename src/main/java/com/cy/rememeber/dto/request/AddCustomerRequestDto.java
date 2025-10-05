package com.cy.rememeber.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCustomerRequestDto {
    private String customerName;
    private String customerPhone;
    private Long storeKey;
    private String memo;
}
