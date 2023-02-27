package cmc.controller;

import cmc.domain.model.OrderType;
import cmc.dto.request.UpdateWorldInfoRequestDto;
import cmc.dto.response.WorldHashtagsUserCountResponseDto;
import cmc.dto.request.SaveWorldRequestDto;
import cmc.domain.World;
import cmc.dto.response.IsMemberOfWorldResponseDto;
import cmc.error.ErrorResponse;
import cmc.service.WorldService;
import cmc.common.ResponseDto;
import cmc.common.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/world")
@Slf4j
@Tag(name = "세계관 컨트롤러")
public class WorldController {

    private final WorldService worldService;

    @Operation(
            summary = "세계관 저장",
            description = "세계관을 저장합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 저장 성공"),
            @ApiResponse(responseCode = "400", description = "이미지 업로드 실패")
    })
    @PostMapping
    public ResponseEntity<ResponseDto> saveWorld(
            @Parameter(description = "세계관 정보", required = true)@RequestPart(value = "data") SaveWorldRequestDto req,
            @Parameter(description = "세계관 이미지", required = true) @RequestParam(value = "file") MultipartFile file,
            Principal principal
            ) {

        Long tokenUserId = Long.parseLong(principal.getName());

        worldService.saveWorld(
                tokenUserId,
                file,
                req.getWorldName(),
                req.getWorldNotice(),
                req.getWorldUserLimit(),
                req.getWorldPassword(),
                req.getHashtags(),
                req.getTodos());

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(ResponseCode.WORLD_SAVE_SUCCESS));
    }

    @Operation(
            summary = "세계관 최신순 조회",
            description = "세계관을 최신순으로 조회합니다." +
                    "`order=recent` 인 경우 최신순을 반환하며 없는 경우에는 id asc 로 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 최신순 조회 성공"),
            @ApiResponse(responseCode = "400", description = "정렬 타입이 잘못되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<ResponseDto<List<WorldHashtagsUserCountResponseDto>>> getWorldsWithOrder(
            @Parameter(description = "세계관 정렬 기준") @RequestParam(value = "order", defaultValue = "id") String order) {

        OrderType orderType = OrderType.fromString(order);

        List<World> worldsWithOrder = worldService.getWorldsWithOrder(orderType);

        List<WorldHashtagsUserCountResponseDto> worldHashtagsUserCountResponseDtoList = worldsWithOrder.stream()
                .map(WorldHashtagsUserCountResponseDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.WORLD_FOUND_SUCCESS, worldHashtagsUserCountResponseDtoList));
    }

    @Operation(
            summary = "세계관 삭제",
            description = "세계관를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 상세 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 세계관입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{worldId}")
    public ResponseEntity<ResponseDto> deleteWorld(
            @Parameter(description = "삭제할 세계관 id", required = true) @PathVariable("worldId") Long worldId) {

        worldService.deleteWorld(worldId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.WORLD_DELETED));
    }

    @PutMapping("/{worldId}/info")
    public ResponseEntity<ResponseDto> updateWorldInfo(
            @RequestBody UpdateWorldInfoRequestDto req,
            @PathVariable("worldId") Long worldId,
            Principal principal
    ) {

        Long tokenUserId = Long.parseLong(principal.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        worldService.updateWorldInfo(
                tokenUserId,
                worldId,
                req.getWorldName(),
                req.getWorldUserLimit(),
                LocalDateTime.parse(req.getWorldStartDate(), formatter),
                LocalDateTime.parse(req.getWorldEndDate(), formatter),
                req.getWorldNotice(),
                req.getWorldPassword(),
                req.getWorldHostUserId());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseCode.WORLD_INFO_UPDATED));
    }

//    // 세계관 정보 (사진) for 방장
//    @PostMapping("/api/v1/world/{worldId}/img")
//    public ResponseEntity<ApiResponse> updateWorldImg() {
//
//    }

    @Operation(
            summary = "세계관 상세 조회",
            description = "세계관 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 상세 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 세계관입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{worldId}")
    public ResponseEntity<ResponseDto<WorldHashtagsUserCountResponseDto>> getWorldByWorldId(
            @Parameter(description = "상세 조회할 세계관 id", required = true) @PathVariable("worldId") Long worldId) {

        World world = worldService.getWorldByWorldId(worldId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.WORLD_FOUND_SUCCESS,
                WorldHashtagsUserCountResponseDto.fromEntity(world)));
    }

    @Operation(
            summary = "세계관 검색",
            description = "키워드로 `해시태그`와 `제목`을 기준으로 세계관 검색합니다." +
                    "해시태그의 경우 완전 일치, 제목의 경우 단어가 포함되어 있으면 검색 결과에 포함됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 상세 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 세계관입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<WorldHashtagsUserCountResponseDto>>> searchWorld(
            @Parameter(description = "검색 키워드") @RequestParam("keyword") String keyword) {

        List<World> worlds= worldService.searchWorldByKeyword(keyword);
        List<WorldHashtagsUserCountResponseDto> dtoList = worlds.stream()
                .map(WorldHashtagsUserCountResponseDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.WORLD_SEARCH_SUCCESS, dtoList));
    }

//    // 추천 세계관 목록 조회
//    @GetMapping("/api/v1/world/{worldId}")
//    public ResponseEntity<ApiResponse<dto>> getRecommendedWorld() {
//
//    }
//
//    // 세계관 인기 해시태그 조회
//    @GetMapping("/api/v1/world/hashtag")
//    public ResponseEntity<ApiResponse<dto>> getPopularHashtags() {
//
//    }
}
