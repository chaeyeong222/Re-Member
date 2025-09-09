package com.cy.rememeber.business.repository;

import com.cy.rememeber.business.Entity.Store;
import java.util.Optional;

import com.cy.rememeber.business.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByUser_SocialId(String storeId);

    Optional<Store> findByUser(User user);

}
