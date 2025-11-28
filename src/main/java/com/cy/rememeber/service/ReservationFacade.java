package com.cy.rememeber.service;
import com.cy.rememeber.Entity.Reservation;
import com.cy.rememeber.dto.request.ReservationRequestDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;
@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationFacade {
    private final RedissonClient redissonClient;
    private final ReservationService reservationService; // 실제 비즈니스 로직 서비스

    public Reservation makeReservation(ReservationRequestDto dto) {

        // 1. 락 키 생성 (예: reservation:store:1:time:202511291400)
        // 가게(storeId)의 특정 시간(timeSlot)은 한 명만 선점해야 하므로 유니크한 키가 필요함
        String lockKey = String.format("reservation:lock:store:%d:time:%s", dto.getStoreKey(), dto.getReservedAt());

        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 2. 락 획득 시도 (waitTime: 락을 기다리는 시간, leaseTime: 락을 잡고 있는 시간)
            // 10초 동안 기다려보고, 못 잡으면 포기. 락을 잡으면 5초 뒤에 자동으로 풀림(Deadlock 방지)
            boolean available = lock.tryLock(5, 10, TimeUnit.SECONDS);

            if (!available) {
                log.warn("락 획득 실패 - 동시 예약 시도: storeKey={}, time={}",
                    dto.getStoreKey(), dto.getReservedAt());
                throw new IllegalStateException("현재 예약 처리 중입니다. 잠시 후 다시 시도해주세요.");
            }

            // 3. 실제 비즈니스 로직 수행
            return reservationService.makeReservation(userId, storeId, timeSlot);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("예약 처리 중 인터럽트 발생", e);
        } finally {
            // 4. 락 해제 >내 락인지 확인하고, 아직 잠겨있으면 해제
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
