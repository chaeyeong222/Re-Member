package com.cy.rememeber.repository;

import com.cy.rememeber.dto.response.FindCustomerDto;
import java.util.List;

public interface QCustomerRepository {
    List<FindCustomerDto> findCustomerByName(String customerName);
}

