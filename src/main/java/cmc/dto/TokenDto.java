package cmc.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private Boolean isSignuped;

    @Builder
    TokenDto(String accessToken, String refreshToken, Boolean isSignuped) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isSignuped = isSignuped;
    }
}
