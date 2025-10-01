package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.dto.response.FindCustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<FindCustomerDto> findByUser_UserNameContaining(String userName);

    List<FindCustomerDto> findByUser_PhoneContaining(String phone);

//    List<FindCustomerDto> findByStore_StoreKey(Long storeKey);
    List<Customer> findByStore_StoreKey(Long storeKey);


//    List<FindCustomerDto> findCustomersByStoreStoreKey(Long storeKey);

    /**
     * 특정 가게의 모든 고객 정보를 User 정보와 함께(Fetch Join) 조회 : N+1 문제를 해결을 위함.
     */
    @Query("SELECT c FROM Customer c JOIN FETCH c.user WHERE c.store.storeKey = :storeKey")
    List<Customer> findAllByStoreKeyWithUser(@Param("storeKey") Long storeKey);
}