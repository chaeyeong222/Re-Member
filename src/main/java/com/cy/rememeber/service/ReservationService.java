package com.cy.rememeber.service;

import com.cy.rememeber.Entity.Reservation;
import com.cy.rememeber.repository.ReservationRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    /**
     * 예약
     * 해당가게, 특정시간, 특정 날짜에 이미 예약돼있으면 예약 불가
     * */
    @Transactional
    public Reservation makeReservation(Long customerId, Long storeKey, LocalDateTime reservedAt) {
        boolean exists = reservationRepository.existsByStoreKeyAndReservationDateTime(storeKey, reservedAt);

        if (exists) {
            throw new IllegalStateException("이미 해당 시간에 예약이 존재합니다.");
        }

        Reservation reservation = new Reservation(customerId, storeKey, reservedAt);
        return reservationRepository.save(reservation);
    }

}
