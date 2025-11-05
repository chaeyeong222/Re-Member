package com.cy.rememeber.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    //예약확인대기중, 예약확정, 예약취소, 방문완료
    PENDING("STATUS_WAITING"),RESERVED("STATUS_RESERVED"), CANCELED("STATUS_CANCELED"), COMPLETED("STATUS_COMPLETED");


    private final String key;
}
