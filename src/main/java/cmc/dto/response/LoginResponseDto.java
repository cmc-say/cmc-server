package cmc.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String accessToken;

    @Builder
    LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
