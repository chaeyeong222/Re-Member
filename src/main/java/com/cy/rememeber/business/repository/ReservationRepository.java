package com.cy.rememeber.business.repository;

import com.cy.rememeber.business.Entity.Reservation;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
        boolean existsByStoreKeyAndReservationDateTime(Long storeKey, LocalDateTime reservedAt);
}
