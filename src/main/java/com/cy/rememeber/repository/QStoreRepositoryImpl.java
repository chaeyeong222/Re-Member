package com.cy.rememeber.repository;

import com.cy.rememeber.dto.response.StoreInfoResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.cy.rememeber.Entity.Store.store;

@Slf4j
@Repository
public class QStoreRepositoryImpl implements QStoreRepository {

    private final JPAQueryFactory queryFactory;

    public QStoreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<StoreInfoResponseDto> findAll(){
        return queryFactory
            .select(Projections.constructor(StoreInfoResponseDto.class,
                    store.storeKey.as("storeKey"),
                    store.phone.as("phone"),
                    store.address.as("address"),
                    store.storeName.as("storeName"),
                    store.introduction.as("introduction"))
                .from(store)
                .fetch();
    }

    @Override
    public List<StoreInfoResponseDto> findByStoreName(String storeName){
        log.info("storeName: " + storeName);
        return queryFactory
            .select(Projections.constructor(StoreInfoResponseDto.class,
                store.storeKey.as("storeKey"),
                store.phone.as("phone"),
                store.address.as("address"),
                store.storeName.as("storeName"),
                store.introduction.as("introduction"))
            .from(store)
            .where(keywordSearch(storeName))
            .fetch();
    }


    private BooleanExpression keywordSearch(String keyword) {
        return keyword == null ? null : store.storeName.contains(keyword);
    }

}
