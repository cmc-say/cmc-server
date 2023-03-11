package cmc.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginRequestDto {
    @Schema(description = "fcm 디바이스 토큰", required = true, defaultValue = "deviceToken", example = "deviceToken")
    private String deviceToken;

    @Schema(description = "소셜 로그인 인가 코드", required = true, defaultValue = "authorizationCode", example = "authorizationCode")
    private String authorizationCode;

    @Schema(description = "소셜 로그인 타입. kakao or apple", required = true, defaultValue = "kakao", example = "kakao")
    private String socialType;
}
