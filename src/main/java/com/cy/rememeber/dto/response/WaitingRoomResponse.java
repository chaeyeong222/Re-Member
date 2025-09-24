package com.cy.rememeber.dto.response;

public record WaitingRoomResponse(
    Long number,
    Long userId,
    String queue,
    Boolean isAllowed,
    String redirectUrl
    ) {}
