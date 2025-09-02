package com.cy.rememeber.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomerDetailDto {
    private Long customerKey; //
    private String customerName; // 고객명
    private String customerPhone; // 휴대폰번호
    private int visitCnt; // 방문횟수
    private String memo; // 메모
    private String lastVisit; // 최근방문

    private String joinDate; // 최초방문
    private int totalSpent; // 총 지출액
    private String favoriteService; // 선호 서비스
    private List<VisitHistoryDto> visitHistory; // 방문 이력
    private List<String> preferences; // 선호 사항
    private List<DetailedMemoDto> detailedMemos; // 상세 메모

    //TODO: 태그기능
    private List<String> tags; // 태그 목록
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class VisitHistoryDto {
    private Long id;
    private String date;
    private String service;
    private int amount;
    private int satisfaction;
    private String memo;
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class DetailedMemoDto {
    private Long id;
    private String date;
    private String content;
    private String category;
}