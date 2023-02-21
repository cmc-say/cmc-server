package cmc.dto.response;

import cmc.domain.Hashtag;
import cmc.domain.World;
import cmc.domain.WorldAvatar;
import cmc.domain.WorldHashtag;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class WorldHashtagsUserCountResponseDto {
    private Long worldId;
    private String worldName;
    private Integer worldUserLimit;
    private String worldImg;
    private LocalDateTime worldStartDate;
    private LocalDateTime worldEndDate;
    private List<HashtagResponseDto> hashtags;
    private Long worldHostUserId;
    private String worldNotice;
    private String worldPassword;
    private List<WorldAvatarResponseDto> worldAvatars;

    @Builder
    public WorldHashtagsUserCountResponseDto(
            Long worldId,
            String worldName,
            Integer worldUserLimit,
            String worldImg,
            LocalDateTime worldStartDate,
            LocalDateTime worldEndDate,
            List<Hashtag> hashtags,
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
        this.hashtags = hashtags.stream().map(HashtagResponseDto::fromEntity).collect(Collectors.toList());
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
                .hashtags(world.getWorldHashtags().stream().map(WorldHashtag::getHashtag).collect(Collectors.toList()))
                .worldAvatars(world.getWorldAvatars())
                .build();
    }
}
