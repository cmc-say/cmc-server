package cmc.controller;

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

    // 세계관에 참여하고 있는 유저인지 조회
    @GetMapping("/api/v1/user/isMember")
    public void isMemberOfWorld(@RequestParam("worldId") Long worldId, Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        userService.isMemberOfWorld()
    }

    // 유저 신고
    @PostMapping("/api/v1/user/{userId}/report")
    public ResponseEntity<ApiResponse> reportUser(@PathVariable("userId") Long userId, Principal principal) {
        log.info("reporting {}", principal.getName() );
        Long tokenUserId = Long.parseLong(principal.getName());
        userService.reportUser(tokenUserId, userId);

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
