package cmc.domain.user.controller;

import cmc.domain.user.service.UserService;
import cmc.global.common.ApiResponse;
import cmc.global.common.ResponseCode;
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

    @DeleteMapping("/api/v1/user")
    public ResponseEntity<ApiResponse> deleteUser() {

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_DELETE_SUCCESS));
    }

    @GetMapping("/api/v1/user/isMember")
    public void isMemberOfWorld(@RequestParam("worldId") Long worldId, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        // world
    }

    @PostMapping("/api/v1/user/{userId}/report")
    public ResponseEntity<ApiResponse> reportUser(@PathVariable("userId") Long userId, Principal principal) {
        log.info("reporting {}", principal.getName() );
        Long tokenUserId = Long.parseLong(principal.getName());
        userService.reportUser(tokenUserId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_REPORT_SUCCESS));
    }

    @PostMapping("/api/v1/user/{userId}/block")
    public ResponseEntity<ApiResponse> blockUser(@PathVariable("userId") Long userId, Principal principal) {
        log.info("block user - authentication name {}", principal.getName());

        Long tokenUserId = Long.parseLong(principal.getName());
        userService.blockUser(tokenUserId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_BLOCK_SUCCESS));
    }

}
