package com.cy.rememeber.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // 현재 트랜잭션이 읽기 전용인지 확인
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        // 읽기 전용이면 SLAVE, 아니면 MASTER 반환
        return isReadOnly ? "SLAVE" : "MASTER";
    }
}