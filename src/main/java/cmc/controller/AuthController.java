package cmc.controller;

import cmc.common.ResponseCode;
import cmc.common.ResponseDto;
import cmc.domain.model.SocialType;
import cmc.dto.request.LoginRequestDto;
import cmc.dto.TokenDto;
import cmc.dto.response.LoginResponseDto;
import cmc.jwt.token.JwtProvider;
import cmc.service.AuthService;
import cmc.utils.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth 컨트롤러")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @Operation(
            summary = "회원 탈퇴",
            description = "토큰에 해당하는 회원을 탈퇴합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteUser(Principal principal) {

        Long tokenUserId = Long.parseLong(principal.getName());

        authService.deleteUser(tokenUserId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_DELETE_SUCCESS));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> loginUser(HttpServletResponse response, @RequestBody LoginRequestDto req) {

        SocialType socialType = SocialType.fromString(req.getSocialType());
        TokenDto tokenDto = authService.loginUser(req.getDeviceToken(), req.getAuthorizationCode(), socialType);

        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        // jwtprovider 에는 ms로 저장되어 있기 때문에 s로 변환
        CookieUtil.addCookie(response, "refreshToken", refreshToken, (int) (jwtProvider.getRefreshTokenValidity()/1000));

        LoginResponseDto dto = LoginResponseDto.builder()
                .accessToken(accessToken)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_LOGIN_SUCCESS, dto));
    }

}
