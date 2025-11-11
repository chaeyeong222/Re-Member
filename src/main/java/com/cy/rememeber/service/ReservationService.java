package com.cy.rememeber.service;

import com.cy.rememeber.Entity.Customer;
import com.cy.rememeber.Entity.Reservation;
import com.cy.rememeber.Entity.ReservationStatus;
import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.Entity.VisitHistory;
import com.cy.rememeber.dto.request.CompleteReservationRequestDto;
import com.cy.rememeber.dto.request.ReservationRequestDto;
import com.cy.rememeber.repository.CustomerRepository;
import com.cy.rememeber.repository.ReservationRepository;
import com.cy.rememeber.repository.StoreRepository;
import com.cy.rememeber.repository.VisitHistoryRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final VisitHistoryRepository visitHistoryRepository;
    private final StoreRepository storeRepository;

    /**
     * 예약 (레거시 - 기존 호환성 유지)
     * 해당가게, 특정시간, 특정 날짜에 이미 예약돼있으면 예약 불가
     * */
    @Transactional
    public Reservation makeReservation(Long customerId, Long storeKey, LocalDateTime reservedAt) {
        boolean exists = reservationRepository.existsByStoreKeyAndReservationDateTime(storeKey, reservedAt);

        if (exists) {
            throw new IllegalStateException("이미 해당 시간에 예약이 존재합니다.");
        }

        Reservation reservation = new Reservation(customerId, storeKey, reservedAt);
        return reservationRepository.save(reservation);
    }

    /**
     * 예약 생성 (예약자 정보 포함)
     * 해당가게, 특정시간, 특정 날짜에 이미 예약돼있으면 예약 불가
     * */
    @Transactional
    public Reservation makeReservation(ReservationRequestDto dto) {
        boolean exists = reservationRepository.existsByStoreKeyAndReservationDateTime(
                dto.getStoreKey(), dto.getReservedAt());

        if (exists) {
            throw new IllegalStateException("이미 해당 시간에 예약이 존재합니다.");
        }

        // customerId는 null (아직 고객 목록에 없는 예약자)
        Reservation reservation = new Reservation(
                null,
                dto.getStoreKey(),
                dto.getReservedAt(),
                dto.getReservationName(),
                dto.getReservationPhone()
        );
        return reservationRepository.save(reservation);
    }

    /**
     * 가게별 전체 예약 목록 조회
     */
    public List<Reservation> getReservationsByStore(Long storeKey) {
        return reservationRepository.findByStoreKey(storeKey);
    }

    /**
     * 가게별 + 상태별 예약 목록 조회
     */
    public List<Reservation> getReservationsByStoreAndStatus(Long storeKey, ReservationStatus status) {
        return reservationRepository.findByStoreKeyAndReservationStatus(storeKey, status);
    }

    /**
     * 가게별 + 특정 날짜의 예약 목록 조회
     */
    public List<Reservation> getReservationsByStoreAndDate(Long storeKey, Timestamp date) {
        return reservationRepository.findByStoreKeyAndDate(storeKey, date);
    }

    /**
     * 예약 상세 조회
     */
    public Reservation getReservation(Long reservationKey) {
        return reservationRepository.findById(reservationKey)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
    }

    /**
     * 예약 상태 변경
     */
    @Transactional
    public Reservation updateReservationStatus(Long reservationKey, ReservationStatus status) {
        Reservation reservation = getReservation(reservationKey);
        reservation.setReservationStatus(status);
        return reservationRepository.save(reservation);
    }

    /**
     * 예약 완료 처리 + 고객 히스토리 자동 추가
     * 고객이 없으면 자동으로 생성
     */
    @Transactional
    public Reservation completeReservation(Long reservationKey, CompleteReservationRequestDto dto) {
        // 1. 예약 조회
        Reservation reservation = getReservation(reservationKey);

        if (reservation.getReservationStatus() == ReservationStatus.COMPLETED) {
            throw new IllegalStateException("이미 완료된 예약입니다.");
        }

        // 2. 예약 정보 업데이트

        log.info("예약정보 업데이트. getReservationName={} getReservationPhone ={} ", dto.getReservationName() ,dto.getReservationPhone());
        reservation.setReservationStatus(ReservationStatus.COMPLETED);
        reservation.setReservationName(dto.getReservationName());
        reservation.setReservationPhone(dto.getReservationPhone());
        reservation.setAmount(dto.getAmount());
        reservation.setMemo(dto.getMemo());
        reservationRepository.save(reservation);

        // 3. 고객 조회 또는 생성
        Customer customer = customerRepository.findByCustomerKey(reservation.getCustomerId());
        if (customer == null) {
            // 고객이 없으면 자동 생성
            log.info("고객이 존재하지 않아 자동 생성합니다. customerId={}", reservation.getCustomerId());

            Store store = storeRepository.findById(reservation.getStoreKey())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));

            // Reservation에 저장된 예약자 정보 사용 (dto에 없으면 reservation에서 가져옴)
            String customerName = dto.getReservationName() != null ? dto.getReservationName() : reservation.getReservationName();
            String customerPhone = dto.getReservationPhone() != null ? dto.getReservationPhone() : reservation.getReservationPhone();

            log.info("고객 생성 정보: name={}, phone={}", customerName, customerPhone);

            Timestamp now = new Timestamp(System.currentTimeMillis());
            customer = Customer.builder()
                    .customerName(customerName)
                    .customerPhone(customerPhone)
                    .store(store)
                    .visitCnt(0)
                    .joinDate(now)
                    .lastVisit(now)
                    .build();
            customer = customerRepository.save(customer);

            // Reservation의 customerId를 새로 생성된 고객의 customerKey로 업데이트
            reservation.setCustomerId(customer.getCustomerKey());
            reservationRepository.save(reservation);

            log.info("고객 생성 완료: customerKey={}, customerName={}", customer.getCustomerKey(), customer.getCustomerName());
        }

        // 4. VisitHistory 자동 생성
        VisitHistory visitHistory = VisitHistory.builder()
                .customer(customer)
                .visitDate(reservation.getReservationDateTime())  // 예약 날짜를 방문 날짜로
                .amount(dto.getAmount())
                .memo(dto.getMemo())
                .build();
        visitHistoryRepository.save(visitHistory);

        // 5. Customer 정보 업데이트
        customer.addVisitCnt();
        customer.setLastVisit(reservation.getReservationDateTime());
        customerRepository.save(customer);

        log.info("예약 완료 처리 완료: reservationKey={}, customerId={}, visitHistory 생성 완료",
                reservationKey, customer.getCustomerKey());

        return reservation;
    }

    /**
     * 예약 취소
     */
    @Transactional
    public Reservation cancelReservation(Long reservationKey) {
        Reservation reservation = getReservation(reservationKey);

        if (reservation.getReservationStatus() == ReservationStatus.COMPLETED) {
            throw new IllegalStateException("이미 완료된 예약은 취소할 수 없습니다.");
        }
        reservation.setReservationStatus(ReservationStatus.CANCELED);
        return reservationRepository.save(reservation);
    }

}
