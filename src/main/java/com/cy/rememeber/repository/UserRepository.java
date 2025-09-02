package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialId(String socialId);
}
