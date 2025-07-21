package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findById(String storeId);

}
