package cmc.domain.User.controller;

import cmc.domain.User.service.UserService;
import cmc.global.common.ApiResponse;
import cmc.global.common.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/characters")
    public void getCharacters(Principal principal) {
        Long tokenUserId = Long.parseLong(principal.getName());
        // get character
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUser() {

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_DELETE_SUCCESS));
    }

    @GetMapping("/isMember")
    public void isMemberOfWorld(@RequestParam("worldId") Long worldId, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        // world
    }

    @PostMapping("/{userId}/report")
    public ResponseEntity<ApiResponse> reportUser(@PathVariable("userId") Long userId, Principal principal) {
        log.info("reporting {}", principal.getName() );
        Long tokenUserId = Long.parseLong(principal.getName());
        userService.reportUser(tokenUserId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_REPORT_SUCCESS));
    }

    @PostMapping("/{userId}/block")
    public ResponseEntity<ApiResponse> blockUser(@PathVariable("userId") Long userId, Principal principal) {
        log.info("block user - authentication name {}", principal.getName());

        Long tokenUserId = Long.parseLong(principal.getName());
        userService.blockUser(tokenUserId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_BLOCK_SUCCESS));
    }

}
