package com.cy.rememeber.service;

import com.cy.rememeber.Entity.Store;
import com.cy.rememeber.Entity.User;
import com.cy.rememeber.dto.StoreSignDto;
import com.cy.rememeber.dto.response.StoreInfoResponseDto;
import com.cy.rememeber.repository.StoreRepository;
import com.cy.rememeber.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    /**
    * 새로운 가게 등록 메서드
     * @param storeSignDto 가게정보
    * */
    public void registerStore(StoreSignDto storeSignDto){
        //유저확인
        User user = userRepository.findBySocialId(storeSignDto.getSocialId())
                .orElseThrow(()->new IllegalArgumentException("User not foudn with socialId : "+storeSignDto.getSocialId()));

        //해당 유저가 이미 가게를 가지고 있는지 확인
        Optional<Store> existingStore = storeRepository.findByUser_SocialId(storeSignDto.getSocialId());
        if(existingStore.isPresent()){
            throw new IllegalStateException("User already has a registered store.");
        }
        Store store = Store.builder()
                .user(user)
                .storeName(storeSignDto.getStoreName())
                .address(storeSignDto.getAddress())
                .introduction(storeSignDto.getIntroduction())
                .build();
        storeRepository.save(store);
    }

    /**
     * 유저의 소셜 ID로 가게 정보를 조회하는 메서드 : 소유한 가게 확인 용도
     * @param socialId 유저 소셜 ID
     * @return Store
     */
    public Store getStoreBySocialId(String socialId) {
        System.out.println("가게잇나?");
        System.out.println(socialId);
        User user = userRepository.findBySocialId(socialId)
            .orElseThrow(() -> new IllegalArgumentException("해당 소셜 ID 유저 없음"));

        return storeRepository.findByUser(user)
            .orElse(null);
    }

    public List<Store> getStoreList(){
        return storeRepository.findTop5ByOrderByStoreKeyAsc();
//        return storeRepository.findAll();
    }

    public List<Store> findStoreByStoreName(String keyword){
        return storeRepository.findByStoreNameContaining(keyword);
    }
    /**
     * 유저의 소셜 ID로 가게 정보를 조회하는 메서드
     * @param storeKey 유저 소셜 ID
     * @return Store
     */
    public Store getStoreByStoreKey(Long storeKey) {
        log.info("storeKey: {} 로 조회", storeKey);
        Store store = storeRepository.findByStoreKey(storeKey)
                .orElseThrow(() -> new IllegalArgumentException("해당 storeKey 정보 없음"));

        return store;
    }
}
