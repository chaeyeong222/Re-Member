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
    private String reservationName;   // 고객 이름 (고객이 없을 경우 자동 생성에 사용)
    private String reservationPhone;  // 고객 전화번호 (고객이 없을 경우 자동 생성에 사용)
}