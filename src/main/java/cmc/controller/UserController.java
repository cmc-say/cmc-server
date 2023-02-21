package cmc.controller;

import cmc.domain.model.ReportType;
import cmc.dto.request.ReportUserRequestDto;
import cmc.service.UserService;
import cmc.common.ApiResponse;
import cmc.common.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // 회원 탈퇴
    @DeleteMapping("/api/v1/user")
    public ResponseEntity<ApiResponse> deleteUser() {

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_DELETE_SUCCESS));
    }

    // 유저 신고
    @PostMapping("/api/v1/user/{userId}/report")
    public ResponseEntity<ApiResponse> reportUser(@PathVariable("userId") Long userId, Principal principal, @RequestBody ReportUserRequestDto req) {

        Long tokenUserId = Long.parseLong(principal.getName());
        ReportType reportType = ReportType.fromString(req.getReportType());

        userService.reportUser(tokenUserId, userId, reportType);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_REPORT_SUCCESS));
    }

    // 유저 차단
    @PostMapping("/api/v1/user/{userId}/block")
    public ResponseEntity<ApiResponse> blockUser(@PathVariable("userId") Long userId, Principal principal) {
        log.info("block user - authentication name {}", principal.getName());

        Long tokenUserId = Long.parseLong(principal.getName());
        userService.blockUser(tokenUserId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_BLOCK_SUCCESS));
    }

}
