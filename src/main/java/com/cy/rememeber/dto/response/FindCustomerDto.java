package com.cy.rememeber.dto.response;

import com.cy.rememeber.Entity.Customer;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FindCustomerDto {
    private Long customerKey;
    private String customerName;
    private String customerPhone;
    private int visitCnt;
    private String memo;
    private String lastVisit;
    private String joinDate; // 최초방문

    public static FindCustomerDto from(Customer customer) {
        return FindCustomerDto.builder()
                .customerKey(customer.getCustomerKey())
                .customerName(customer.getCustomerName())
                .customerPhone(customer.getCustomerPhone())
                .visitCnt(customer.getVisitCnt())
                .memo(customer.getMemo())
                .lastVisit(customer.getLastVisit() != null ? customer.getLastVisit().toString() : null)
            .joinDate(customer.getJoinDate() != null ? customer.getJoinDate().toString() : null)
            .build();
    }



}
