package com.cy.rememeber.service;

import com.cy.rememeber.dto.response.CustomerDetailDto;
import com.cy.rememeber.dto.response.FindCustomerDto;
import com.cy.rememeber.repository.QCustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final QCustomerRepository QcustomerRepository;

    public List<FindCustomerDto> getCustomers(){
        return QcustomerRepository.findAll();
    }

    /**
     * 고객 조회 - 이름
     * */
    public List<FindCustomerDto> getCustomer(String customerName){
        return QcustomerRepository.findCustomerByName(customerName);
    }

    /**
     * 고객 조회 - 번호
     * */
    public List<FindCustomerDto> getPhone(String customerPhone){
        return QcustomerRepository.findByPhoneNum(customerPhone);
    }

    public CustomerDetailDto getCustomerDetailById(Long customerKey) {
        // 1. customerKey를 이용해 고객 기본 정보 엔티티를 조회
        // 2. 해당 고객의 방문 이력(visitHistory), 상세 메모(detailedMemos), 선호사항(preferences) 등
        //    관련 엔티티들을 모두 조회
        // 3. 이 엔티티들을 모두 모아 CustomerDetailDto 객체를 생성하여 반환합니다.
        //    만약 고객이 존재하지 않으면 null을 반환
        return new CustomerDetailDto();
    }



}
