package cmc.controller;

import cmc.dto.request.SaveAvatarRequestDto;
import cmc.dto.response.AvatarResponseDto;
import cmc.domain.Avatar;
import cmc.service.AvatarService;
import cmc.common.ApiResponse;
import cmc.common.ResponseCode;
import cmc.utils.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AvatarController {
    private final AvatarService avatarService;
    private final S3Util s3Util;

    // 유저의 캐릭터들 조회
    @GetMapping("/api/v1/user/avatars")
    public ResponseEntity<ApiResponse<List<AvatarResponseDto>>> getAvatars(Principal principal) {
        Long tokenUserId = Long.parseLong(principal.getName());
        List<Avatar> avatars = avatarService.getCharactersByUserId(tokenUserId);

        List<AvatarResponseDto> saveAvatarResponse = avatars.stream().map(AvatarResponseDto::fromEntity).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_CHARACTERS_FOUND, saveAvatarResponse));
    }

    // 캐릭터 저장 (사진, 정보)
    @PostMapping("/api/v1/avatar")
    public ResponseEntity<ApiResponse> saveAvatar(
            @RequestPart(value = "data") SaveAvatarRequestDto req,
            @RequestPart(value = "file") MultipartFile file,
            Principal principal) {
        Long tokenUserId = Long.parseLong(principal.getName());
        avatarService.saveAvatar(tokenUserId, req.getAvatarName(), req.getAvatarMessage(), file);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.AVATAR_SAVE_SUCCESS));
    }
//
//    // 캐릭터 수정 (사진) PUT /{characterId}/img
//    @PutMapping("/{characterId}/img")
//    public ResponseEntity<ApiResponse> updateCharacterImg(@RequestPart(value = "file") MultipartFile file) {
//        // delete 원래 파일
//        // upload 현재 파일
//    }
//
//    // 캐릭터 수정 (정보) PUT /{characterId}/info
//    @PutMapping("/{characterId}/info")
//    public ResponseEntity<ApiResponse> updateCharacterInfo() {
//
//    }
//
//    // 캐릭터 정보 조회 GET /{characterId}
//    @GetMapping("/{characterId}")
//    public ResponseEntity<ApiResponse<dto>> getCharacter() {
//
//    }
//
//    // 캐릭터 한달 달성 프로그래스바 조회 GET /{characterId}/progress
//    @GetMapping("/{characterId}/progress")
//    public ResponseEntity<ApiResponse<dto>> getCharacterProgress() {
//
//    }
//
//
//    // 오늘의 한마디 쓰기 POST /{characterId}/wordToday?worldId=
//    @PostMapping("/{characterId}/wordToday")
//    public ResponseEntity<ApiResponse> saveWordToday() {
//
//    }
//
//    // 오늘의 한마디 조회 GET /{characterId}/wordToday?worldId=
//    @GetMapping("/{characterId}/wordToday")
//    public ResponseEntity<ApiResponse<dto>> getWordToday() {
//
//    }
//
//    // 캐릭터의 세계관 체크리스트 및 체크 여부 조회 /{characterId}/todos?worldId=
//    @GetMapping("/{characterId}/todos")
//    public ResponseEntity<ApiResponse<dto>> getTodosOfCharacterToday() {
//
//    }
//
//    // 체크리스트 체크하기 /{characterId}/todo/{todoId}
//    @PostMapping("{characterId}/todo/{todoId}")
//    public ResponseEntity<ApiResponse<dto>> checkTodo() {
//
//    }

}
