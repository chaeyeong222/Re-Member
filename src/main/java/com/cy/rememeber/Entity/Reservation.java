package com.cy.rememeber.Entity;

import com.cy.rememeber.service.ReservationService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "reservation")
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

    // 예약 완료 시 입력할 방문 정보 (고객 히스토리 연동을 위함)
    private Integer amount;  // 이용 금액 (nullable - 완료 시 입력)
    private String memo;     // 메모 (nullable - 완료 시 입력)

    public Reservation(Long customerId, Long storeKey, LocalDateTime reservedAt) {
        this.customerId = customerId;
        this.storeKey = storeKey;
        this.reservationDateTime = Timestamp.valueOf(reservedAt);
        this.reservationStatus = ReservationStatus.RESERVED;
    }
}
