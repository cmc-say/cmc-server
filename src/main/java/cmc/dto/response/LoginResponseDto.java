package cmc.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String accessToken;
    private Boolean isSignuped;

    @Builder
    LoginResponseDto(String accessToken, Boolean isSignuped) {
        this.accessToken = accessToken;
        this.isSignuped = isSignuped;
    }
}
