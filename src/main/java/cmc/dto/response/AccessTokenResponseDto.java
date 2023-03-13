package cmc.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccessTokenResponseDto {
    private String accessToken;

    @Builder
    AccessTokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
