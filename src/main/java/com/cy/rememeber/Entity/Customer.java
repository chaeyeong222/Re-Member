package com.cy.rememeber.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Entity
@SuperBuilder
@Table(name = "Customer")
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@DynamicInsert
public class Customer {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerKey; //��Ű
    private String customerName; //����
    private String customerPhone; //�޴�����ȣ
    private int visitCnt; //�湮Ƚ��
    private String memo; //�޸�
    private Timestamp lastVisit; //�ֱٹ湮
    private Timestamp joinDate; //���ʹ湮

    private Long storeKey;
    //TODO : tag ���
}




