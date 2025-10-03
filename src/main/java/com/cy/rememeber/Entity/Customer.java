package com.cy.rememeber.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import java.util.PriorityQueue;

@Entity
@SuperBuilder
@Table(name = "customer")
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
    private Long customerKey; //고객키
    private String customerName;
    private String customerPhone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_key")
    private Store store; //이 고객이 속한 가게

    private int visitCnt; //방문횟수
    private String memo; //메모
    private Timestamp lastVisit; //최근방문
    private Timestamp joinDate; //최초방문


    //TODO : tag 기능
    private String tags; // 예: "VIP,정기고객,추천인"
    // 편의 메서드
    public void addVisitCnt() {
        this.visitCnt++;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

}




