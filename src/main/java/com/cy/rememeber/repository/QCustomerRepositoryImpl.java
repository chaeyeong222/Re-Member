package com.cy.rememeber.repository;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.dto.response.FindCustomerDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import static com.cy.rememeber.Entity.QCustomer.customer;

@Slf4j
@Repository
public class QCustomerRepositoryImpl implements QCustomerRepository{
    private final JPAQueryFactory queryFactory;

    public QCustomerRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<FindCustomerDto> findCustomerByName(String customerName) {
        log.info("customerName: " + customerName);
        return queryFactory
            .select(Projections.constructor(FindCustomerDto.class,
                customer.customerName.as("customerName"),
                customer.visitCnt.as("visitCnt"),
                customer.memo.as("memo")))
            .from(customer)
            .where(keywordSearch(customerName))
            .fetch();
    }

    private BooleanExpression keywordSearch(String keyWord){
        return keyWord==null? null: customer.customerName.contains(keyWord);
    }

    @Override
    public List<FindCustomerDto> findByPhoneNum(String phone){
        return  queryFactory
            .select(Projections.constructor(FindCustomerDto.class,
                customer.customerKey.as("customerKey"),
                customer.visitCnt.as("visitCnt"),
                customer.memo.as("memo")))
            .from(customer)
            .where(keywordSearch(phone))
            .fetch();
    }

    @Override
    public List<FindCustomerDto> findAll(){
        return queryFactory.select(Projections.constructor(FindCustomerDto.class,
            customer.customerKey.as("customerKey"),
            customer.customerPhone.as("customerPhone"),
            customer.customerName.as("name"),
            customer.memo.as("memo"),
            customer.visitCnt.as("visitCnt")))
            .from(customer)
            .fetch();
    }

    @Override
    public List<FindCustomerDto> getCustomerByStoreKey(Long storeKey) {
        return queryFactory
                .select(Projections.constructor(FindCustomerDto.class,
                        customer.customerKey.as("customerKey"),
                        customer.customerPhone.as("customerPhone"),
                        customer.customerName.as("name"),
                        customer.memo.as("memo"),
                        customer.visitCnt.as("visitCnt")))
                .from(customer)
                .where(storekeySearch(storeKey))
                .fetch();
    }

    private BooleanExpression storekeySearch(Long storeKey){
        return storeKey==null? null: customer.storeKey.eq(storeKey);
    }

}