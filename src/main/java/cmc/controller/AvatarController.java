package cmc.controller;

import cmc.domain.World;
import cmc.dto.request.SaveAvatarRequestDto;
import cmc.dto.response.AvatarResponseDto;
import cmc.domain.Avatar;
import cmc.dto.response.WorldHashtagsUserCountResponseDto;
import cmc.service.AvatarService;
import cmc.common.ResponseDto;
import cmc.common.ResponseCode;
import cmc.utils.S3Util;
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
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/avatar")
@Tag(name = "Avatar 컨트롤러")
public class AvatarController {
    private final AvatarService avatarService;
    private final S3Util s3Util;


    @Operation(
            summary = "캐릭터 저장",
            description = "캐릭터를 저장합니다. 이미지 크기 제한은 5MB 입니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터 저장 성공"),
            @ApiResponse(responseCode = "400", description = "이미지 업로드 실패")
    })
    @PostMapping
    public ResponseEntity<ResponseDto> saveAvatar(
            @RequestPart(value = "data") SaveAvatarRequestDto req,
            @Parameter(description = "이미지 사진", required = true) @RequestPart(value = "file") MultipartFile file,
            Principal principal) {
        Long tokenUserId = Long.parseLong(principal.getName());
        avatarService.saveAvatar(tokenUserId, req.getAvatarName(), req.getAvatarMessage(), file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.AVATAR_SAVE_SUCCESS));
    }

    @Operation(
            summary = "캐릭터가 갖고있는 세계관 리스트 조회",
            description = "캐릭터가 갖고있는 세계관 리스트 조회를 합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터가 가지고 있는 세계관 조회에 성공하였습니다.")
    })
    @GetMapping("/{avatarId}/worlds")
    public ResponseEntity<ResponseDto<List<WorldHashtagsUserCountResponseDto>>> getWorldsByAvatar(
            @Parameter(description = "조회하는 아바타 id", required = true) @PathVariable("avatarId") Long avatarId
    ) {

        List<World> worldHashtags = avatarService.getWorldsByAvatar(avatarId);
        List<WorldHashtagsUserCountResponseDto> worldHashtagsResponse = worldHashtags.stream()
                .map(WorldHashtagsUserCountResponseDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.WORLD_WITH_CHARACTER_FOUND_SUCCESS, worldHashtagsResponse));
    }


    // 캐릭터 수정 (사진) PUT /{characterId}/img
    @PostMapping("/{avatarId}/img")
    public ResponseEntity<ResponseDto> updateCharacterImg(
            @PathVariable("avatarId") Long avatarId,
            @RequestPart(value = "file") MultipartFile file
    ) {

        avatarService.updateAvatarImg(avatarId, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(ResponseCode.AVATAR_IMG_UPDATED));
    }

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
