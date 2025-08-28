package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Reservation;
import com.cy.rememeber.Entity.Store;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//    Optional<Store> existsByStoreIdAndReservedAt(Long storeKey, LocalDateTime dateTime);
        boolean existsByStoreIdAndReservedAt(Long storeKey, LocalDateTime dateTime);
}
