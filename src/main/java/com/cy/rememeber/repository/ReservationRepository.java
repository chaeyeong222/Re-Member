package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Reservation;
import com.cy.rememeber.Entity.ReservationStatus;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByStoreKeyAndReservationDateTime(Long storeKey, LocalDateTime reservedAt);

    // 가게별 전체 예약 목록 조회
    List<Reservation> findByStoreKey(Long storeKey);

    // 가게별 + 상태별 예약 목록 조회
    List<Reservation> findByStoreKeyAndReservationStatus(Long storeKey, ReservationStatus status);

    // 가게별 + 특정 날짜의 예약 목록 조회
    @Query("SELECT r FROM Reservation r WHERE r.storeKey = :storeKey " +
           "AND DATE(r.reservationDateTime) = DATE(:date)")
    List<Reservation> findByStoreKeyAndDate(@Param("storeKey") Long storeKey,
                                            @Param("date") Timestamp date);

//    // 가게별 + 날짜 범위 예약 조회
//    @Query("SELECT r FROM Reservation r WHERE r.storeKey = :storeKey " +
//           "AND r.reservationDateTime BETWEEN :startDate AND :endDate")
//    List<Reservation> findByStoreKeyAndDateRange(@Param("storeKey") Long storeKey,
//                                                  @Param("startDate") Timestamp startDate,
//                                                  @Param("endDate") Timestamp endDate);
}
