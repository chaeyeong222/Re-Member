package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.VisitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitHistoryRepository extends JpaRepository<VisitHistory, Long> {
    List<VisitHistory> findByCustomer_CustomerKey(Long customerKey);
}
