package com.cy.rememeber.service;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.Entity.VisitHistory;
import com.cy.rememeber.dto.request.AddVisitHistoryRequestDto;
import com.cy.rememeber.repository.CustomerRepository;
import com.cy.rememeber.repository.VisitHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitHistoryService {
    private final VisitHistoryRepository visitHistoryRepository;
    private final CustomerRepository customerRepository;

    public List<VisitHistory> getVisitHistoryByCustomerKey(Long customerKey){
        return visitHistoryRepository.findByCustomer_CustomerKey(customerKey);
    }

    @Transactional
    public VisitHistory addVisitHistory(AddVisitHistoryRequestDto dto) {
        // 1. 고객 조회
        Customer customer = customerRepository.findByCustomerKey(dto.getCustomerKey());
        if (customer == null) {
            throw new IllegalArgumentException("존재하지 않는 고객입니다.");
        }

        Timestamp visitTimestamp = convertStringToTimestamp(dto.getVisitDate());

        VisitHistory visitHistory = VisitHistory.builder()
                .customer(customer)
                .visitDate(visitTimestamp)
                .amount(dto.getAmount())
                .memo(dto.getMemo())
                .build();

        customer.addVisitCnt();                    // visitCnt++
        customer.setLastVisit(visitTimestamp);     // 최근 방문일 업데이트
        customerRepository.save(customer);

        return visitHistoryRepository.save(visitHistory);
    }

    private Timestamp convertStringToTimestamp(String dateString) {
        try {
            // "2025-01-30 00:00:00" 형식 처리 (공백 구분)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
            return Timestamp.valueOf(localDateTime);
        } catch (Exception e) {
            try {
                // "2025-10-30" 형식 처리 (날짜만)
                LocalDateTime localDateTime = LocalDateTime.parse(dateString + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                return Timestamp.valueOf(localDateTime);
            } catch (Exception e3) {
                throw new IllegalArgumentException("잘못된 날짜 형식입니다. (지원 형식: 2025-01-30 00:00:00, 2025-10-30)");
            }
        }
    }
}
