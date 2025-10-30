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
public class AddVisitHistoryRequestDto {
    private Long customerKey;
    private String visitDate;
    private int amount;
    private String memo;
}
