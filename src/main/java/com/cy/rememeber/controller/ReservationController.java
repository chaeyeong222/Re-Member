package com.cy.rememeber.controller;

import com.cy.rememeber.Entity.Reservation;
import com.cy.rememeber.service.ReservationService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// ReservationController.java
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 처리
    @PostMapping
    public ResponseEntity<Reservation> createReservation(
        @RequestParam Long userId,
        @RequestParam Long storeKey,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservedAt) {

        log.info("reservationInfo: userId={}, storeKey={}, reservedAt={}",userId, storeKey, reservedAt);
        Reservation newReservation = reservationService.makeReservation(userId, storeKey, reservedAt);
        return ResponseEntity.ok(newReservation);
    }
}