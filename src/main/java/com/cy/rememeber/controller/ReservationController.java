package com.cy.rememeber.controller;

import com.cy.rememeber.Entity.Reservation;
import com.cy.rememeber.service.ReservationService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
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
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 처리
    @PostMapping
    public ResponseEntity<Reservation> createReservation(
        @RequestParam Long customerId,
        @RequestParam Long storeId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservedAt) {

        Reservation newReservation = reservationService.makeReservation(customerId, storeId, reservedAt);
        return ResponseEntity.ok(newReservation);
    }
}