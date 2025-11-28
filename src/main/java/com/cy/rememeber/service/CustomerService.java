package com.cy.rememeber.service;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.dto.request.AddCustomerRequestDto;
import com.cy.rememeber.dto.response.FindCustomerDto;
import com.cy.rememeber.repository.CustomerRepository;
import com.cy.rememeber.repository.StoreRepository;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;

    /**
     * 고객 조회 - 이름
     * */
    @Transactional(readOnly = true)
    public List<FindCustomerDto> getCustomer(String customerName){
        return customerRepository.findByCustomerNameContaining(customerName);
    }

    /**
     * 고객 조회 - 번호$
     * */
    @Transactional(readOnly = true)
    public List<FindCustomerDto> getPhone(String customerPhone){
        return customerRepository.findByCustomerPhoneContaining(customerPhone);
    }

    @Transactional(readOnly = true)
    public List<FindCustomerDto> getAllCustomers(Long storeKey) {
        List<Customer> customers = customerRepository.findByStore_StoreKey(storeKey);

        return customers.stream()
                .map(FindCustomerDto::from) // 정적 팩토리 메소드 참조
                .collect(Collectors.toList());
    }

    public FindCustomerDto getUserByCustomerKey(Long customerKey) {
        Customer customer = customerRepository.findByCustomerKey(customerKey);
        return FindCustomerDto.from(customer);
    }

    public Customer addCustomer(AddCustomerRequestDto addCustomerRequestDto) {
        Store store = storeRepository.findById(addCustomerRequestDto.getStoreKey())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));

        Timestamp now = new Timestamp(System.currentTimeMillis());

        Customer customer = Customer.builder()
            .customerName(addCustomerRequestDto.getCustomerName())
            .customerPhone(addCustomerRequestDto.getCustomerPhone())
            .store(store)
            .memo(addCustomerRequestDto.getMemo())
            .visitCnt(0)
            .joinDate(now)
            .lastVisit(now)
            .build();
        return customerRepository.save(customer);
    }

}
