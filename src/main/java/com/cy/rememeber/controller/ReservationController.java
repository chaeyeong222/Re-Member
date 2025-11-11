package com.cy.rememeber.controller;

import com.cy.rememeber.Entity.Reservation;
import com.cy.rememeber.Entity.ReservationStatus;
import com.cy.rememeber.dto.request.CompleteReservationRequestDto;
import com.cy.rememeber.dto.request.ReservationRequestDto;
import com.cy.rememeber.service.ReservationService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 생성 (예약자 정보 포함)
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequestDto dto) {
        log.info("예약 생성: store={}, name={}, phone={}, time={}",
                dto.getStoreKey(), dto.getReservationName(), dto.getReservationPhone(), dto.getReservedAt());
        Reservation newReservation = reservationService.makeReservation(dto);
        return ResponseEntity.ok(newReservation);
    }

    // 예약 생성 (레거시 - 기존 호환성 유지)
    @PostMapping("/legacy")
    public ResponseEntity<Reservation> createReservationLegacy(
        @RequestParam Long userId,
        @RequestParam Long storeKey,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservedAt) {

        log.info("reservationInfo (legacy): userId={}, storeKey={}, reservedAt={}",userId, storeKey, reservedAt);
        Reservation newReservation = reservationService.makeReservation(userId, storeKey, reservedAt);
        return ResponseEntity.ok(newReservation);
    }

    // 가게별 전체 예약 목록 조회
    @GetMapping("/store/{storeKey}")
    public ResponseEntity<List<Reservation>> getReservationsByStore(@PathVariable Long storeKey) {
        List<Reservation> reservations = reservationService.getReservationsByStore(storeKey);
        return ResponseEntity.ok(reservations);
    }

    // 가게별 + 상태별 예약 목록 조회
    @GetMapping("/store/{storeKey}/status/{status}")
    public ResponseEntity<List<Reservation>> getReservationsByStoreAndStatus(
            @PathVariable Long storeKey,
            @PathVariable ReservationStatus status) {
        List<Reservation> reservations = reservationService.getReservationsByStoreAndStatus(storeKey, status);
        return ResponseEntity.ok(reservations);
    }

    // 가게별 + 특정 날짜의 예약 목록 조회
    @GetMapping("/store/{storeKey}/date")
    public ResponseEntity<List<Reservation>> getReservationsByStoreAndDate(
            @PathVariable Long storeKey,
            @RequestParam String date) {
        // "2025-01-30" 형식을 Timestamp로 변환
        Timestamp timestamp = Timestamp.valueOf(date + " 00:00:00");
        List<Reservation> reservations = reservationService.getReservationsByStoreAndDate(storeKey, timestamp);
        return ResponseEntity.ok(reservations);
    }

    // 예약 상세 조회
    @GetMapping("/{reservationKey}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long reservationKey) {
        Reservation reservation = reservationService.getReservation(reservationKey);
        return ResponseEntity.ok(reservation);
    }

    // 예약 완료 처리 (+ 고객 히스토리 자동 추가)
    @PutMapping("/{reservationKey}/complete")
    public ResponseEntity<Reservation> completeReservation(
            @PathVariable Long reservationKey,
            @RequestBody CompleteReservationRequestDto dto) {
        log.info("예약 완료 처리 요청: reservationKey={}, amount={}, memo={}, customerName={}, customerPhone={}",
                reservationKey, dto.getAmount(), dto.getMemo(), dto.getReservationName(), dto.getReservationPhone());
        Reservation reservation = reservationService.completeReservation(reservationKey, dto);
        return ResponseEntity.ok(reservation);
    }

    // 예약 취소
    @PutMapping("/{reservationKey}/cancel")
    public ResponseEntity<Reservation> cancelReservation(@PathVariable Long reservationKey) {
        log.info("예약 취소: reservationKey={}", reservationKey);
        Reservation reservation = reservationService.cancelReservation(reservationKey);
        return ResponseEntity.ok(reservation);
    }

    // 예약 상태 변경 (범용)
    @PutMapping("/{reservationKey}/status")
    public ResponseEntity<Reservation> updateReservationStatus(
            @PathVariable Long reservationKey,
            @RequestParam ReservationStatus status) {
        log.info("예약 상태 변경: reservationKey={}, status={}", reservationKey, status);
        Reservation reservation = reservationService.updateReservationStatus(reservationKey, status);
        return ResponseEntity.ok(reservation);
    }
}