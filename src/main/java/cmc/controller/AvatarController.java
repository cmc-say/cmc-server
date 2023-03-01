package cmc.controller;

import cmc.domain.World;
import cmc.dto.request.SaveAvatarRequestDto;
import cmc.dto.request.UpdateAvatarRequestDto;
import cmc.dto.response.AvatarResponseDto;
import cmc.domain.Avatar;
import cmc.dto.response.WorldHashtagsUserCountResponseDto;
import cmc.error.ErrorResponse;
import cmc.service.AvatarService;
import cmc.common.ResponseDto;
import cmc.common.ResponseCode;
import cmc.utils.S3Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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


    @Operation(
            summary = "캐릭터 이미지 수정",
            description = "캐릭터의 이미지를 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터 이미지가 업데이트 되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 캐릭터가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{avatarId}/img")
    public ResponseEntity<ResponseDto> updateCharacterImg(
            @Parameter(description = "수정하는 캐릭터 id", required = true) @PathVariable("avatarId") Long avatarId,
            @Parameter(description = "수정하는 캐릭터 이미지", required = true) @RequestPart(value = "file") MultipartFile file
    ) {

        avatarService.updateAvatarImg(avatarId, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(ResponseCode.AVATAR_IMG_UPDATED));
    }

    @Operation(
            summary = "캐릭터 정보 수정",
            description = "캐릭터의 이미지를 제외한 모든 정보를 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터 정보가 업데이트 되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 캐릭터가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{avatarId}/info")
    public ResponseEntity<ResponseDto> updateCharacterInfo(
            @Parameter(description = "수정하려는 캐릭터 id", required = true) @PathVariable("avatarId") Long avatarId,
            @RequestBody UpdateAvatarRequestDto req
            ) {

        avatarService.updateAvatarInfo(avatarId, req.getAvatarName(), req.getAvatarMessage());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.AVATAR_INFO_UPDATED));
    }

    @Operation(
            summary = "캐릭터 정보 조회",
            description = "캐릭터의 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터 정보 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "해당 캐릭터가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{avatarId}")
    public ResponseEntity<ResponseDto<AvatarResponseDto>> getAvatarByAvatarId(
            @Parameter(description = "조회할 캐릭터 id", required = true) @PathVariable("avatarId") Long avatarId
    ) {

        Avatar avatar = avatarService.getAvatarByAvatarId(avatarId);
        AvatarResponseDto dto = AvatarResponseDto.fromEntity(avatar);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.AVATAR_FOUND, dto));
    }

    @Operation(
            summary = "캐릭터 삭제",
            description = "캐릭터를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "해당 캐릭터가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{avatarId}")
    public ResponseEntity<ResponseDto> deleteAvatarById(
            @Parameter(description = "삭제할 캐릭터 id", required = true) @PathVariable("avatarId") Long avatarId
    ) {
        avatarService.deleteAvatarById(avatarId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.AVATAR_DELETED));
    }

    @Operation(
            summary = "캐릭터가 세계관에 참여",
            description = "캐릭터가 세계관에 참여합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "캐릭터가 세계관에 참여하였습니다."),
            @ApiResponse(responseCode = "400", description = "해당 캐릭터는 이미 세계관에 참여하고 있습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{avatarId}/world/{worldId}")
    public ResponseEntity<ResponseDto> enterWorld(
            @Parameter(description = "세계관에 참여하려는 캐릭터 id", required = true) @PathVariable("avatarId") Long avatarId,
            @Parameter(description = "참여하려는 세계관 id", required = true) @PathVariable("worldId") Long worldId
    ) {

        avatarService.enterWorld(avatarId, worldId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(ResponseCode.AVATAR_ENTER_WORLD));
    }

    @Operation(
            summary = "캐릭터가 세계관에서 탈퇴",
            description = "캐릭터가 세계관에서 탈퇴합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "캐릭터가 세계관에서 탈퇴하였습니다."),
            @ApiResponse(responseCode = "400", description = "해당 캐릭터는 세계관에 참여하고 있지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{avatarId}/world/{worldId}")
    public ResponseEntity<ResponseDto> quitWorld(
            @Parameter(description = "세계관에 참여하려는 캐릭터 id", required = true) @PathVariable("avatarId") Long avatarId,
            @Parameter(description = "참여하려는 세계관 id", required = true) @PathVariable("worldId") Long worldId
    ) {
        avatarService.quitWorld(avatarId, worldId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.AVATAR_QUIT_WORLD));
    }

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
