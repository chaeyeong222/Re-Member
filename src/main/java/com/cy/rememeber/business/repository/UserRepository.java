package com.cy.rememeber.business.repository;

import com.cy.rememeber.business.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialId(String socialId);

    Optional<User> findByPhone(String phone);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByRefreshToken(String refreshToken);


}
