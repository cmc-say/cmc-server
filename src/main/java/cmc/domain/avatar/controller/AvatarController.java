package cmc.domain.avatar.controller;

import cmc.domain.avatar.entity.Avatar;
import cmc.domain.avatar.service.AvatarService;
import cmc.global.common.ApiResponse;
import cmc.global.common.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AvatarController {
    private final AvatarService avatarService;

    // GET
    @GetMapping("/api/v1/user/avatars")
    public ResponseEntity<ApiResponse<Avatar>> getCharacters(Principal principal) {
        Long tokenUserId = Long.parseLong(principal.getName());
        List<Avatar> avatars = avatarService.getCharactersByUserId(tokenUserId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_CHARACTERS_FOUND, avatars));
    }

//    // 캐릭터 추가 (사진, 정보) POST
//    @PostMapping
//    public ResponseEntity<ApiResponse> saveCharacter(
//            @RequestPart(value = "data", required = true) SaveCharacterRequest req,
//            @RequestPart(value = "file") MultipartFile file,
//            Principal principal) {
//
//    }
//
//    // 캐릭터 수정 (사진) PUT /{characterId}/img
//    @PutMapping("/{characterId}/img")
//    public ResponseEntity<ApiResponse> updateCharacterImg() {
//
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
//    // 캐릭터가 속해있는 세계관 리스트 조회 GET /{characterId}/worlds
//    @GetMapping("/{characterId}/worlds")
//    public ResponseEntity<ApiResponse<dto>> getWorldsWithCharacter() {
//
//    }
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
