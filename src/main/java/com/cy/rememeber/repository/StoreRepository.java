package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.dto.response.StoreInfoResponseDto;
import java.util.List;
import java.util.Optional;

import com.cy.rememeber.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByUser_SocialId(String storeId);

    Optional<Store> findByUser(User user);

    List<StoreInfoResponseDto> findAllBy();

}
