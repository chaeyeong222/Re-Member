# Re-Member(리멤버)
> 1인 사업자를 위한 예약 및 고객 관리 서비스

[![배포 URL](https://img.shields.io/badge/배포-https://re--member.store-blue)](https://re-member.store)

- 기간 :  2025.07 - 2025.10

  ## 📌 프로젝트 소개
  Re:Member는 1인 사업자를 위한 예약 관리 및 고객 관리 시스템입니다.

- **고객**: 웹 페이지를 통해 손쉽게 예약
- **사업자**: 대시보드에서 예약 현황과 고객 관리를 직관적으로 확인
- **핵심**: Redis 기반 대기열 시스템으로 동시 접속 시에도 안정적인 예약 처리

## 🛠 기술 스택

### Backend
![Java](https://img.shields.io/badge/Java-17-007396?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)
![Redis](https://img.shields.io/badge/Redis-7.x-DC382D?logo=redis)

### Frontend
![Next.js](https://img.shields.io/badge/Next.js-14-000000?logo=nextdotjs)
![TypeScript](https://img.shields.io/badge/TypeScript-5.x-3178C6?logo=typescript)
![TailwindCSS](https://img.shields.io/badge/TailwindCSS-3.x-06B6D4?logo=tailwindcss)

### Infrastructure
![AWS EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?logo=amazonec2)
![AWS RDS](https://img.shields.io/badge/AWS%20RDS-527FFF?logo=amazonrds)
![Vercel](https://img.shields.io/badge/Vercel-000000?logo=vercel)
![Nginx](https://img.shields.io/badge/Nginx-009639?logo=nginx)

### Tools 
![GitHub](https://img.shields.io/badge/GitHub-181717?logo=github) 
![JMeter](https://img.shields.io/badge/Apache%20JMeter-D22128?logo=apachejmeter)

## 🏗 시스템 아키텍처
<img width="521" height="431" alt="Image" src="https://github.com/user-attachments/assets/3a3e3432-b435-484f-a4a0-238bf18e056d" />

## ✨ 주요 기능

### 고객용 기능
- 🔍 가게 검색 및 예약
- 📊 실시간 대기 순번 확인
- 🔐 소셜 로그인 (카카오)

### 사업자용 기능
- 📅 예약 현황 관리
- 👥 고객 정보 관리 (방문 히스토리, 메모)
- 📈 예약 통계 대시보드
- 🏪 가게 등록 및 정보 수정

### 특징
- ⚡ Redis 대기열 시스템으로 동시성 제어
- 🔄 고객 ↔ 사업자 역할 동적 전환 가능
- 대기열 주요 로직
<img width="600" height="400" alt="Image" src="https://github.com/user-attachments/assets/6be19cb8-9561-44e6-8540-81454ed5d6ef" />

---
# 📱 화면 구성

## 고객 화면
### 가게 목록 검색, 예약 시 대기열 등록
<img src="https://github.com/user-attachments/assets/ec69263f-4ec3-4137-bb65-504801c2452b"  width="400" height="800"/>

### 예약하기
<img src="https://github.com/user-attachments/assets/c6c9fd29-cf37-4076-a667-59a1ecd6c2e7"  width="400" height="800"/>

## 사업자 화면
### 고객 목록 (관리)
<img src="https://github.com/user-attachments/assets/d4f7f01c-2fa2-4564-89aa-536122ac88db"  width="400" height="800"/>
<img src="https://github.com/user-attachments/assets/6b191249-cf12-4f8a-8ccc-991dc0ad8809"  width="400" height="800"/>

### 고객 별 히스토리 관리
<img src="https://github.com/user-attachments/assets/1501a5e0-e990-4597-bdd9-7177dc9f56d7"  width="400" height="800"/>
<img src="https://github.com/user-attachments/assets/fbe6a2b3-cad8-42a2-ba53-633f4671ae50"  width="400" height="800"/>

### 새로운 고객 추가
<img src="https://github.com/user-attachments/assets/9910fa6f-ef78-4499-b72d-ef2360a9a861"  width="400" height="800"/>

### 고객 예약 처리 시 히스토리 자동 연동
<img src="https://github.com/user-attachments/assets/1d4bf536-04e1-4a9e-a054-208ee8cb460c"  width="400" height="800"/>

## 성능테스트
- jmeter 활용
  
<img src="https://github.com/user-attachments/assets/2f7539cb-b9e9-4e10-ab2a-9821f579f307"  width="400" height="800"/>

> 날짜별, 상태별 조회 가능
<img src="https://github.com/user-attachments/assets/5ce5b95e-4972-42ce-acde-8e1d60491f50"  width="400" height="800"/>
