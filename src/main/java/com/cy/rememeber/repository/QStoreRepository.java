package com.cy.rememeber.repository;
import com.cy.rememeber.dto.response.StoreInfoResponseDto;
import java.util.List;
public interface QStoreRepository {
    List<StoreInfoResponseDto> findAll();

    List<StoreInfoResponseDto> findByStoreName(String keyword);
}
