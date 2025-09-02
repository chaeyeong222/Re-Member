package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Reservation;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//    Optional<Store> existsByStoreIdAndReservedAt(Long storeKey, LocalDateTime dateTime);
        boolean existsByStoreIdAndReservedAt(Long storeKey, LocalDateTime dateTime);
}
