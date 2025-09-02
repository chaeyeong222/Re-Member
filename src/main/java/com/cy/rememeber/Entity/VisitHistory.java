package com.cy.rememeber.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "visit_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class VisitHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_key", nullable = false)
    private Customer customer; // 방문 이력 대상 고객

    @Column(nullable = false)
    private Timestamp visitDate; // 방문 날짜

    private int amount; // 지불 금액

    private String memo; // 이 방문에 대한 간단한 메모
}