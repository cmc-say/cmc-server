package cmc.controller;

import cmc.domain.model.OrderType;
import cmc.dto.response.WorldHashtagsUserCountResponseDto;
import cmc.dto.request.SaveWorldRequestDto;
import cmc.domain.World;
import cmc.dto.response.IsMemberOfWorldResponseDto;
import cmc.service.WorldService;
import cmc.common.ApiResponse;
import cmc.common.ResponseCode;
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
public class WorldController {

    private final WorldService worldService;

    // 세계관 만들기
    @PostMapping("/api/v1/world")
    public ResponseEntity<ApiResponse> saveWorld(
            @RequestPart(value = "data") SaveWorldRequestDto req,
            @RequestPart(value = "file") MultipartFile file,
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

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ResponseCode.WORLD_SAVE_SUCCESS));
    }

    // 캐릭터가 속해있는 세계관 리스트 조회
    @GetMapping("/api/v1/world/avatar/{avatarId}")
    public ResponseEntity<ApiResponse<List<WorldHashtagsUserCountResponseDto>>> getWorldsByAvatar(@PathVariable("avatarId") Long avatarId) {

        List<World> worldHashtags = worldService.getWorldsByAvatar(avatarId);
        List<WorldHashtagsUserCountResponseDto> worldHashtagsRespons = worldHashtags.stream()
                .map(WorldHashtagsUserCountResponseDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.WORLD_WITH_CHARACTER_FOUND_SUCCESS, worldHashtagsRespons));
    }


    // 세계관에 참여하고 있는 유저인지 조회
    @GetMapping("/api/v1/user/world/{worldId}/isMember")
    public ResponseEntity<ApiResponse<IsMemberOfWorldResponseDto>> isMemberOfWorld(@PathVariable("worldId") Long worldId, Principal principal) {

        Long userId = Long.parseLong(principal.getName());

        boolean isMember = !worldService.isMemberOfWorldByUserId(userId, worldId).isEmpty();
        IsMemberOfWorldResponseDto isMemberOfWorldResponseDto = new IsMemberOfWorldResponseDto(isMember);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.USER_IS_MEMBER_OF_WORLD_FOUND, isMemberOfWorldResponseDto));
    }


    // 세계관 최신순 조회
    @GetMapping("/api/v1/world")
    public ResponseEntity<ApiResponse<List<WorldHashtagsUserCountResponseDto>>> getWorldsWithOrder(
            @RequestParam(value = "order", defaultValue = "id") String order) {

        OrderType orderType = OrderType.fromString(order);

        List<World> worldsWithOrder = worldService.getWorldsWithOrder(orderType);

        List<WorldHashtagsUserCountResponseDto> worldHashtagsUserCountResponseDtoList = worldsWithOrder.stream()
                .map(WorldHashtagsUserCountResponseDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseCode.WORLD_FOUND_SUCCESS, worldHashtagsUserCountResponseDtoList));
    }

    // 세계관 삭제
    @DeleteMapping("/api/v1/world/{worldId}")
    public ResponseEntity<ApiResponse> deleteWorld(@PathVariable("worldId") Long worldId) {

        worldService.deleteWorld(worldId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseCode.WORLD_DELETED));
    }
//
//    // 세계관 수정 (정보) for 방장
//    @PutMapping("/api/v1/world/{worldId}/info")
//    public ResponseEntity<ApiResponse> updateWorldInfo() {
//
//    }
//
//    // 세계관 정보 (사진) for 방장
//    @PostMapping("/api/v1/world/{worldId}/img")
//    public ResponseEntity<ApiResponse> updateWorldImg() {
//
//    }
//
//    // 세계관 상세 정보 조회
//    @GetMapping("/api/v1/world/{worldId}")
//    public ResponseEntity<ApiResponse<dto>> getWorld() {
//
//    }
//
//    // 세계관 검색 결과 조회
//    @GetMapping("/api/v1/world/search")
//    public ResponseEntity<ApiResponse<dto>> searchWorld() {
//
//    }
//
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
