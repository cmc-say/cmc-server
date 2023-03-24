package cmc.controller;

import cmc.common.ResponseCode;
import cmc.common.ResponseDto;
import cmc.domain.model.SocialType;
import cmc.dto.request.LoginRequestDto;
import cmc.dto.TokenDto;
import cmc.dto.response.LoginResponseDto;
import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteUser(Principal principal) {

        Long tokenUserId = Long.parseLong(principal.getName());

        authService.deleteUser(tokenUserId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_DELETE_SUCCESS));
    }

    @Operation(
            summary = "로그인",
            description = "socialType은 kakao, apple, google 중 하나이며 이미 가입한 회원의 경우 isSignuped = true" +
                    "\t\n refresh token은 쿠키에 Path=/; HttpOnly; Expires=(3개월 후) 로 저장됩니다.;"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 social 로그인 타입입니다." +
                    "\t\n소셜 access token를 가져오는데 실패하였습니다." +
                    "\t\n소셜 access token를 통해 소셜 id를 가져오는데 실패하였습니다." +
                    "\t\n유효하지 않은 소셜 로그인 인가코드 입니다.")
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> loginUser(HttpServletResponse response, @RequestBody LoginRequestDto req) {

        SocialType socialType = SocialType.fromString(req.getSocialType());
        TokenDto tokenDto = authService.loginUser(req.getDeviceToken(), req.getSocialId(), socialType);

        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();
        Boolean isSignuped = tokenDto.getIsSignuped();

        // jwtprovider 에는 ms로 저장되어 있기 때문에 s로 변환
        CookieUtil.addCookie(response, "refreshToken", refreshToken, (int) (jwtProvider.getRefreshTokenValidity()/1000));

        LoginResponseDto dto = LoginResponseDto.builder()
                .accessToken(accessToken)
                .isSignuped(isSignuped)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_LOGIN_SUCCESS, dto));
    }

    @Operation(
            summary = "토큰 재발급",
            description = "쿠키의 리프래시 토큰을 db와 대조하여 어세스 토큰을 재발급합니다." +
                    "\t\n access 토큰을 재발급하지만 리프래시 토큰은 갱신되지 않습니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "재발급 성공"),
            @ApiResponse(responseCode = "400", description = "쿠기에 리프래시 토큰이 존재하지 않습니다." +
                    "\t\n디비에 해당 리프래시 토큰이 존재하지 않습니다." +
                    "\t\n리프래시 토큰이 만료되었습니다.")
    })
    @PostMapping("/reissue")
    public ResponseEntity<ResponseDto<LoginResponseDto>> reissueAccessToken(HttpServletRequest request) {
        Cookie refreshToken = CookieUtil.getCookie(request, "refreshToken")
                .orElseThrow(() -> new BusinessException(ErrorCode.COOKIE_REFRESH_TOKEN_NOT_FOUND));

        String accessToken =  authService.reissueAccessToken(refreshToken.getValue());

        LoginResponseDto dto = LoginResponseDto.builder()
                .accessToken(accessToken)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.ACCESS_TOKEN_REISSUED, dto));
    }
}
