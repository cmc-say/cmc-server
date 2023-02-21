package cmc.dto.response;

import cmc.domain.Hashtag;
import cmc.domain.World;
import cmc.domain.WorldHashtag;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetWorldsByAvatarResponse {
    private Long worldId;
    private String worldName;
    private Integer worldUserLimit;
    private String worldImg;
    private LocalDateTime worldStartDate;
    private LocalDateTime worldEndDate;
    private List<Hashtag> hashtag;
    private Long worldHostUserId;
    private String worldNotice;
    private String worldPassword;

    @Builder
    public GetWorldsByAvatarResponse(
            Long worldId,
            String worldName,
            Integer worldUserLimit,
            String worldImg,
            LocalDateTime worldStartDate,
            LocalDateTime worldEndDate,
            List<Hashtag> hashtag,
            Long worldHostUserId,
            String worldNotice,
            String worldPassword
            ) {
        this.worldId = worldId;
        this.worldName = worldName;
        this.worldUserLimit = worldUserLimit;
        this.worldImg = worldImg;
        this.worldStartDate = worldStartDate;
        this.worldEndDate = worldEndDate;
        this.hashtag = hashtag;
        this.worldHostUserId = worldHostUserId;
        this.worldNotice = worldNotice;
        this.worldPassword = worldPassword;
    }

    public static GetWorldsByAvatarResponse fromEntity(World world) {
        return GetWorldsByAvatarResponse.builder()
                .worldId(world.getWorldId())
                .worldName(world.getWorldName())
                .worldUserLimit(world.getWorldUserLimit())
                .worldImg(world.getWorldImg())
                .worldStartDate(world.getWorldStartDate())
                .worldEndDate(world.getWorldEndDate())
                .worldHostUserId(world.getWorldHostUserId())
                .worldNotice(world.getWorldNotice())
                .worldPassword(world.getWorldPassword())
                .hashtag(world.getWorldHashtags().stream().map(WorldHashtag::getHashtag).collect(Collectors.toList()))
                .build();
    }
}
