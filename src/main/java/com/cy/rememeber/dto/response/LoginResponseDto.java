package com.cy.rememeber.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    @JsonProperty("isNewUser")
    private boolean newUser;
    private String socialId;
    private String nickname;

    private Long userKey;
}
