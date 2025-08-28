package com.cy.rememeber.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    //예약완료, 예약취소, 방문완료
    RESERVED("STATUS_RESERVED"), CANCELED("STATUS_CANCELED"), COMPLETED("STATUS_COMPLETED");

    private final String key;
}
