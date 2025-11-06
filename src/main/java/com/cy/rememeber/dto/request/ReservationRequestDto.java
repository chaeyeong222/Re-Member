package com.cy.rememeber.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {
    private Long storeKey;
    private String reservationName;     // 예약자 이름
    private String reservationPhone;    // 예약자 연락처
    private LocalDateTime reservedAt;   // 예약 날짜/시간
}