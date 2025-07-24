package com.cy.rememeber.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomer is a Querydsl query type for Customer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomer extends EntityPathBase<Customer> {

    private static final long serialVersionUID = -1433159364L;

    public static final QCustomer customer = new QCustomer("customer");

    public final NumberPath<Long> customerKey = createNumber("customerKey", Long.class);

    public final StringPath customerName = createString("customerName");

    public final StringPath customerPhone = createString("customerPhone");

    public final StringPath memo = createString("memo");

    public final NumberPath<Integer> visitCnt = createNumber("visitCnt", Integer.class);

    public QCustomer(String variable) {
        super(Customer.class, forVariable(variable));
    }

    public QCustomer(Path<? extends Customer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomer(PathMetadata metadata) {
        super(Customer.class, metadata);
    }

}

