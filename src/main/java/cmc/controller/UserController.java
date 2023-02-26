package cmc.controller;

import cmc.domain.model.ReportType;
import cmc.dto.request.ReportUserRequestDto;
import cmc.service.UserService;
import cmc.common.ResponseDto;
import cmc.common.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/user")
@Tag(name = "User 컨트롤러")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "회원 탈퇴",
            description = "토큰에 해당하는 회원을 탈퇴합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteUser(Principal principal) {

        Long tokenUserId = Long.parseLong(principal.getName());

        userService.deleteUser(tokenUserId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_DELETE_SUCCESS));
    }

    @Operation(
            summary = "회원 신고",
            description = "param userId에 해당하는 회원을 신고합니다. \n" +
                    "회원 신고(닉네임 or 상태메세지)가 5번 누적됐을 경우 신고 당한 유저의 캐릭터들" +
                    "모두 캐릭터 닉네임과 캐릭터 상태메세지가 각각 `차단된 유저`와 `차단된 상태메세지` 로 변경됩니다.  "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 신고 성공"),
            @ApiResponse(responseCode = "400", description = "user not found"),
            @ApiResponse(responseCode = "400", description = "중복된 신고입니다."),
            @ApiResponse(responseCode = "400", description = "신고하는 유저와 당하는 유저가 같습니다."),
            @ApiResponse(responseCode = "400", description = "신고 타입이 잘못되었습니다.")
    })
    @PostMapping("/{userId}/report")
    public ResponseEntity<ResponseDto> reportUser(
            @Parameter(description = "신고 당하는 유저 id", required = true) @PathVariable("userId") Long reportedUserId,
            @RequestBody ReportUserRequestDto req,
            Principal principal) {

        Long reportingUserId = Long.parseLong(principal.getName());
        ReportType reportType = ReportType.fromString(req.getReportType());

        userService.reportUser(reportingUserId, reportedUserId, reportType);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_REPORT_SUCCESS));
    }

    @Operation(
            summary = "회원 차단",
            description = "param userId에 해당하는 회원을 차단합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 차단 성공"),
            @ApiResponse(responseCode = "400", description = "user not found"),
            @ApiResponse(responseCode = "400", description = "차단하는 유저와 당하는 유저가 같습니다.")
    })
    @PostMapping("/{userId}/block")
    public ResponseEntity<ResponseDto> blockUser(
            @Parameter(description = "차단 당하는 유저 아이디", required = true) @PathVariable("userId") Long userId,
            Principal principal) {

        Long tokenUserId = Long.parseLong(principal.getName());
        userService.blockUser(tokenUserId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_BLOCK_SUCCESS));
    }

}
