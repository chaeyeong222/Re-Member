package com.cy.rememeber.repository;

import com.cy.rememeber.dto.response.FindCustomerDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//import static com.cy.rememeber.Entity.Customer.customer;
//
//@Slf4j
//@Repository
//public class QCustomerRepositoryImpl implements QCustomerRepository{
//    private final JPAQueryFactory queryFactory;
//
//    public QCustomerRepositoryImpl(EntityManager em){
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//    @Override
//    public List<FindCustomerDto> findCustomerByName(String customerName) {
//        log.info("customerName: " + customerName);
//        return queryFactory
//            .select(Projections.constructor(FindCustomerDto.class,
//                customer.customerName.as("customerName"),
//                cutomer.visitCnt.as("visitCnt"),
//                customer.memo.as("memo")))
//            .from(customer)
//            .where(keywordSearch(customerName))
//            .fetch();
//    }
//}