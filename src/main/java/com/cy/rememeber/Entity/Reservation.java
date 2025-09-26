package com.cy.rememeber.Entity;

import com.cy.rememeber.service.ReservationService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationKey;
    private Long customerId;
    private Long storeKey;

    private String reservationName;
    private Timestamp reservationDateTime;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    public Reservation(Long customerId, Long storeKey, LocalDateTime reservedAt) {
        this.customerId = customerId;
        this.storeKey = storeKey;
        this.reservationDateTime = Timestamp.valueOf(reservedAt);
        this.reservationStatus = ReservationStatus.RESERVED;
    }
}
