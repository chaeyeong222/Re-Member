package com.cy.rememeber.business.dto.response;


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
    private String customerName; // ����
    private String customerPhone; // �޴�����ȣ
    private int visitCnt; // �湮Ƚ��
    private String memo; // �޸�
    private String lastVisit; // �ֱٹ湮

    private String joinDate; // ���ʹ湮
    private int totalSpent; // �� �����
    private String favoriteService; // ��ȣ ����
    private List<VisitHistoryDto> visitHistory; // �湮 �̷�
    private List<String> preferences; // ��ȣ ����
    private List<DetailedMemoDto> detailedMemos; // �� �޸�

    //TODO: �±ױ��
    private List<String> tags; // �±� ���
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