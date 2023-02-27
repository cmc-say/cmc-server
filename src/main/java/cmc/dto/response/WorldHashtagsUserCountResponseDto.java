package cmc.dto.response;

import cmc.domain.World;
import cmc.domain.WorldAvatar;
import cmc.domain.WorldHashtag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WorldHashtagsUserCountResponseDto {
    @Schema(description = "세계관 id")
    private Long worldId;

    @Schema(description = "세계관 이름")
    private String worldName;

    @Schema(description = "세계관 제한 인원")
    private Integer worldUserLimit;

    @Schema(description = "세계관 이미지 url")
    private String worldImg;

    @Schema(description = "세계관 시작하는 datetime")
    private LocalDateTime worldStartDate;

    @Schema(description = "세계관 끝나는 datetime")
    private LocalDateTime worldEndDate;

    @Schema(description = "세계관에 해당되는 해시태그")
    private List<WorldHashtagResponseDto> hashtags;

    @Schema(description = "세계관 방장 유저 id")
    private Long worldHostUserId;

    @Schema(description = "세계관 공지")
    private String worldNotice;

    @Schema(description = "세계관 비밀번호")
    private String worldPassword;

    @Schema(description = "세계관에 참여하는 캐릭터")
    private List<WorldAvatarResponseDto> worldAvatars;

    @Builder
    public WorldHashtagsUserCountResponseDto(
            Long worldId,
            String worldName,
            Integer worldUserLimit,
            String worldImg,
            LocalDateTime worldStartDate,
            LocalDateTime worldEndDate,
            List<WorldHashtag> hashtags,
            Long worldHostUserId,
            String worldNotice,
            String worldPassword,
            List<WorldAvatar> worldAvatars
            ) {
        this.worldId = worldId;
        this.worldName = worldName;
        this.worldUserLimit = worldUserLimit;
        this.worldImg = worldImg;
        this.worldStartDate = worldStartDate;
        this.worldEndDate = worldEndDate;
        this.hashtags = hashtags.stream().map(WorldHashtagResponseDto::fromEntity).collect(Collectors.toList());
        this.worldHostUserId = worldHostUserId;
        this.worldNotice = worldNotice;
        this.worldPassword = worldPassword;
        this.worldAvatars = worldAvatars.stream().map(WorldAvatarResponseDto::fromEntity).collect(Collectors.toList());
    }

    public static WorldHashtagsUserCountResponseDto fromEntity(World world) {
        return WorldHashtagsUserCountResponseDto.builder()
                .worldId(world.getWorldId())
                .worldName(world.getWorldName())
                .worldUserLimit(world.getWorldUserLimit())
                .worldImg(world.getWorldImg())
                .worldStartDate(world.getWorldStartDate())
                .worldEndDate(world.getWorldEndDate())
                .worldHostUserId(world.getWorldHostUserId())
                .worldNotice(world.getWorldNotice())
                .worldPassword(world.getWorldPassword())
                .hashtags(world.getWorldHashtags())
                .worldAvatars(world.getWorldAvatars())
                .build();
    }
}
