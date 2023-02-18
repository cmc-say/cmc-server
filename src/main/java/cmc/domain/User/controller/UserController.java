package cmc.domain.User.controller;

import cmc.domain.User.entity.Block;
import cmc.domain.User.entity.Report;
import cmc.domain.User.service.UserService;
import cmc.global.common.ApiResponse;
import cmc.global.common.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

//    @GetMapping("/characters")
//    public ResponseEntity<> getCharacters() {
//
//    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUser() {

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_DELETE_SUCCESS));
    }

//    @GetMapping("/isMember")
//    public ResponseEntity<> isMemberOfWorld(@RequestParam("worldId") Long worldId) {
//
//    }

    @PostMapping("/{userId}/report")
    public ResponseEntity<ApiResponse> reportUser(@PathVariable("userId") Long userId) {

        userService.reportUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_REPORT_SUCCESS));
    }

    @PostMapping("/{userId}/block")
    public ResponseEntity<ApiResponse> blockUser(@PathVariable("userId") Long userId) {

        userService.blockUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_BLOCK_SUCCESS));
    }

}
