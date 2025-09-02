package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Store;
import java.util.Optional;

import com.cy.rememeber.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findBySocialId(String storeId);

    Optional<Store> findByUser(User user);

}
