package com.cy.rememeber.service;

import com.cy.rememeber.Entity.VisitHistory;
import com.cy.rememeber.repository.VisitHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitHistoryService {
    private final VisitHistoryRepository visitHistoryRepository;

    public List<VisitHistory> getVisitHistoryByCustomerKey(Long customerKey){
        return visitHistoryRepository.findByCustomer_CustomerKey(customerKey);
    }
}
