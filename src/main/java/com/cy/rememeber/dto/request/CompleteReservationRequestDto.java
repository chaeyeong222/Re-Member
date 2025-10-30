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
/**
 * @Description : 예약한 고객을 방문완료 처리할때 사용 > 고객 히스토리와 연동을 위해 입력필요
 * */
public class CompleteReservationRequestDto {
    private Integer amount;
    private String memo;
}