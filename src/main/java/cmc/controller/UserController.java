package cmc.controller;

import cmc.domain.model.ReportType;
import cmc.dto.request.ReportUserRequestDto;
import cmc.service.UserService;
import cmc.common.ResponseDto;
import cmc.common.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "User 컨트롤러")
public class UserController {

    private final UserService userService;

    // 회원 탈퇴
    @DeleteMapping("/api/v1/user")
    @ApiResponses({
            ()
    })
    public ResponseEntity<ResponseDto> deleteUser(Principal principal) {

        Long tokenUserId = Long.parseLong(principal.getName());

        userService.deleteUser(tokenUserId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_DELETE_SUCCESS));
    }

    // 유저 신고
    @PostMapping("/api/v1/user/{userId}/report")
    public ResponseEntity<ResponseDto> reportUser(@PathVariable("userId") Long reportedUserId, Principal principal, @RequestBody ReportUserRequestDto req) {

        Long reportingUserId = Long.parseLong(principal.getName());
        ReportType reportType = ReportType.fromString(req.getReportType());

        userService.reportUser(reportingUserId, reportedUserId, reportType);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_REPORT_SUCCESS));
    }

    // 유저 차단
    @PostMapping("/api/v1/user/{userId}/block")
    public ResponseEntity<ResponseDto> blockUser(@PathVariable("userId") Long userId, Principal principal) {
        log.info("block user - authentication name {}", principal.getName());

        Long tokenUserId = Long.parseLong(principal.getName());
        userService.blockUser(tokenUserId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.USER_BLOCK_SUCCESS));
    }

}
