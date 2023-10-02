package cmc.controller;

import cmc.domain.*;
import cmc.domain.model.OrderType;
import cmc.dto.request.UpdateDeletedWorldHashtagsRequestDto;
import cmc.dto.request.UpdateNewWorldHashtagsRequestDto;
import cmc.dto.request.UpdateWorldInfoRequestDto;
import cmc.dto.response.*;
import cmc.dto.request.SaveWorldRequestDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/world")
@Slf4j
@Tag(name = "World 컨트롤러")
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> saveWorld(
            @ModelAttribute SaveWorldRequestDto req,
            Principal principal
    ) {
        Long tokenUserId = Long.parseLong(principal.getName());

        worldService.saveWorld(
                tokenUserId,
                req.getWorldImg(),
                req.getWorldName(),
                req.getWorldNotice(),
                req.getWorldUserLimit(),
                req.getWorldPassword(),
                req.getHashtags(),
                req.getTodos(),
                req.getRecommendedWorldId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(ResponseCode.WORLD_SAVE_SUCCESS));
    }

    @Operation(
            summary = "세계관 저장",
            description = "세계관을 저장합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 저장 성공"),
            @ApiResponse(responseCode = "400", description = "이미지 업로드 실패")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/refactor")
    public ResponseEntity<ResponseDto> refactor_saveWorld(
            @ModelAttribute SaveWorldRequestDto req,
            Principal principal
    ) {
        Long tokenUserId = Long.parseLong(principal.getName());

        worldService.refactor_saveWorld(
                tokenUserId,
                req.getWorldImg(),
                req.getWorldName(),
                req.getWorldNotice(),
                req.getWorldUserLimit(),
                req.getWorldPassword(),
                req.getHashtags(),
                req.getTodos(),
                req.getRecommendedWorldId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(ResponseCode.WORLD_SAVE_SUCCESS));
    }

    @Operation(
            summary = "세계관 전체 조회",
            description = "세계관을 전체 조회합니다." +
                    "\t\n 만료된 세계관은 조회에서 제외됩니다." +
                    "\t\n`order=recent` 인 경우 최신순을 반환하며 없는 경우에는 id asc 로 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 전체 조회 성공"),
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

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.WORLD_DELETED));
    }

    @Operation(
            summary = "세계관 정보 수정",
            description = "세계관의 해시태그와 세계관 이미지를 제외한 정보를 수정합니다." +
                    "\t\n 방장이 아닌 경우 400 에러 됩니다. " +
                    "\t\n 해시태그가 중복되지 않도록 데이터를 보내야합니다." +
                    "\t\n*(세계관 방장 넘겨주기 기능은 후순위 기능이므로 그대로 픽스 해주세요)* "
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 상세 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "설정하려는 user limit 가 현재의 세계관 인원보다 작습니다., 방장 유저가 아닌 유저는 접근 권한이 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{worldId}/info")
    public ResponseEntity<ResponseDto> updateWorldInfo(
            @RequestBody UpdateWorldInfoRequestDto req,
            @Parameter(description = "수정하려는 세계관 id", required = true) @PathVariable("worldId") Long worldId,
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

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.WORLD_INFO_UPDATED));
    }

    @Operation(
            summary = "세계관 이미지 수정",
            description = "param 으로 들어오는 세계관의 이미지를 수정합니다. \n " +
                    "\t\n사진 크기 제한은 5MB 입니다." +
                    "\t\n방장이 아닌 경우 Unauthorized 됩니다. "
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "세계관 이미지 수정 성공"),
            @ApiResponse(responseCode = "400", description = "파일이 제한 크기를 초과하였습니다." +
                    "\t\n파일 업로드에 실패하였습니다. " +
                    "\t\n방장 유저가 아닌 유저는 접근 권한이 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{worldId}/img")
    public ResponseEntity<ResponseDto> updateWorldImg(
            @Parameter(description = "세계관 id", required = true) @PathVariable Long worldId,
            @Parameter(description = "세계관 이미지", required = true) @RequestParam(value = "file") MultipartFile file,
            Principal principal
    ) {
        Long tokenUserId = Long.parseLong(principal.getName());

        worldService.updateWorldImg(tokenUserId, worldId, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(ResponseCode.WORLD_IMG_UPDATED));
    }

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
                    "\t\n 해시태그의 경우 완전 일치, 제목의 경우 단어가 포함되어 있으면 검색 결과에 포함됩니다." +
                    "\t\n`order=recent` 인 경우 최신순을 반환하며 없는 경우에는 id asc 로 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 상세 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 세계관입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<WorldHashtagsUserCountResponseDto>>> searchWorld(
            @Parameter(description = "검색 키워드") @RequestParam("keyword") String keyword,
            @Parameter(description = "세계관 정렬 기준") @RequestParam(value = "order", defaultValue = "id") String order
    ) {
        OrderType orderType = OrderType.fromString(order);

        List<World> worlds= worldService.searchWorldByKeyword(keyword, orderType);
        List<WorldHashtagsUserCountResponseDto> dtoList = worlds.stream()
                .map(WorldHashtagsUserCountResponseDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.WORLD_SEARCH_SUCCESS, dtoList));
    }

    @Operation(
            summary = "세계관 해시태그 수정 (추가)",
            description = "세계관 수정 화면에서 새로 추가되는 해시태그들에 대한 api입니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 해시태그 수정 (추가) 에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 세계관입니다" +
                    "\t\n방장 유저가 아닌 유저는 접근 권한이 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{worldId}/hashtags/save")
    public ResponseEntity<ResponseDto> updateNewWorldHashtags(
            Principal principal,
            @Parameter(description = "수정할 세계관 id", required = true) @PathVariable("worldId") Long worldId,
            @RequestBody UpdateNewWorldHashtagsRequestDto req
            ) {

        Long tokenUserId = Long.parseLong(principal.getName());

        worldService.updateNewWorldHashtags(tokenUserId, worldId, req.getHashtags());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.WORLD_HASHTAG_CREATED));
    }

    @Operation(
            summary = "세계관 해시태그 수정 (삭제)",
            description = "세계관 수정 화면에서 삭제될 해시태그들에 대한 api입니다. " +
                    "\t\n삭제될 해시태그의 worldHashtagId(세계관-해시태그 매핑 id) 를 list 로 보내주세요."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 해시태그 수정 (삭제) 에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 세계관입니다" +
                    "\t\n 방장 유저가 아닌 유저는 접근 권한이 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{worldId}/hashtags/delete")
    public ResponseEntity<ResponseDto> updateDeletedWorldHashtags(
            Principal principal,
            @Parameter(description = "수정할 세계관 id", required = true) @PathVariable("worldId") Long worldId,
            @RequestBody UpdateDeletedWorldHashtagsRequestDto req
    ) {

        Long tokenUserId = Long.parseLong(principal.getName());

        worldService.updateDeletedWorldHashtags(tokenUserId, worldId, req.getWorldHashtagIds());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.WORLD_HASHTAG_DELETED));
    }


    @Operation(
            summary = "해시태그 정렬하여 조회 (인기 해시태그)",
            description = "해시태그를 세계관에 많이 매핑된 순서로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해시태그 정렬로 조회에 성공하였습니다.")
    })
    @GetMapping("/hashtags")
    public ResponseEntity<ResponseDto<List<HashtagResponseDto>>> getPopularHashtags(
            @Parameter(description = "해시태그 정렬 기준" +
                    "\t\n`order=popular` 인 경우 인기순을 반환하며 없는 경우에는 id asc 로 반환합니다." +
                    "\t\n 인기순의 경우 만료된 세계관도 카운팅에 포함됩니다."
            ) @RequestParam(value = "order", defaultValue = "id") String order) {

        OrderType orderType = OrderType.fromString(order);

        List<Hashtag> popularHashtags = worldService.getPopularHashtags(orderType);
        List<HashtagResponseDto> dtoList = popularHashtags.stream().map(HashtagResponseDto::fromEntity).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.HASHTAG_IN_ORDER_FOUND, dtoList));
    }

    @Operation(
            summary = "세계관 todo 당 오늘의 체크 개수 조회",
            description = "세계관에 속해있는 모든 캐릭터들에 한해 todo 당 오늘의 체크 개수를 조회합니다. " +
                    "\t\n 체크를 아무도 안했다면 count 는 0 으로 보내집니다 ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "세계관 캐릭터 전체의 체크리스트 달성 현황 조회")
    })
    @GetMapping("/{worldId}/todo/today")
    public ResponseEntity<ResponseDto<List<CountCheckedTodoResponseDto>>> getWorldTodoToday(
            @Parameter(description = "조회할 세계관 아이디", required = true) @PathVariable("worldId") Long worldId
    ) {

        List<CountCheckedTodoResponseDto> todos = worldService.getWorldTodoToday(worldId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.WORLD_TODO_TODAY_FOUND, todos));
    }

    @Operation(
            summary = "차단한 유저를 제외한 세계관 속 캐릭터 리스트 조회",
            description = "차단한 유저를 제외한 세계관 속 캐릭터 리스트 조회를 조회합니다. " +
                    "\t\n 오늘의 한마디 존재 여부도 같이 반환합니다. " )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "차단한 유저를 제외한 세계관 속 캐릭터 리스트 조회")
    })
    @GetMapping("/{worldId}/avatars")
    public ResponseEntity<ResponseDto<List<AvatarsInWorldResponseDto>>> getAvatarsByWorldIdWithoutBlockedUser(
            Principal principal,
            @Parameter(description = "조회할 세계관 아이디", required = true) @PathVariable("worldId") Long worldId
    ) {
        Long tokenUserId = Long.parseLong(principal.getName());

        List<AvatarsInWorldResponseDto> avatars = worldService.getAvatarsByWorldIdWithoutBlockedUser(tokenUserId, worldId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.AVATAR_IN_WORLD_WITHOUT_BLOCKED_USER_FOUND, avatars));
    }

    @Operation(
            summary = "추천 세계관 조회"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추천 세계관 조회")
    })
    @GetMapping("/recommended")
    public ResponseEntity<ResponseDto<List<RecommendedWorld>>> getRecommendedWorld() {
        List<RecommendedWorld> recommendedWorld = worldService.getRecommendedWorld();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.RECOMMENDED_WORLD_FOUND, recommendedWorld));
    }

    @Operation(
            summary = "추천 todo 조회"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추천 todo 조회")
    })
    @GetMapping("/{recommendedWorldId}/todo/recommended")
    public ResponseEntity<ResponseDto<List<RecommendedTodo>>> getRecommendedTodo(
            @PathVariable("recommendedWorldId") Long recommendedWorldId
    ) {
        List<RecommendedTodo> recommendedTodos = worldService.getRecommendedTodo(recommendedWorldId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.RECOMMENDED_TODO_FOUND, recommendedTodos));
    }
}
