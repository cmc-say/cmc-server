package cmc.controller;

import cmc.common.ResponseCode;
import cmc.common.ResponseDto;
import cmc.domain.model.SocialType;
import cmc.dto.request.LoginRequestDto;
import cmc.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth 컨트롤러")
public class AuthController {

    private final AuthService authService;

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
    public ResponseEntity<ResponseDto> loginUser(@RequestBody LoginRequestDto req) {

        SocialType socialType = SocialType.fromString(req.getSocialType());
        authService.loginUser(req.getDeviceToken(), req.getAuthorizationCode(), socialType);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_LOGIN_SUCCESS));
    }

}
