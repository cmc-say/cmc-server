package cmc.dto.response;

import cmc.domain.WorldAvatar;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class WorldAvatarResponseDto {
    @Schema(description = "세계관 아바타 중간 테이블의 pk")
    private Long worldAvatarId;

    @Schema(description = "세계관 id")
    private Long worldId;

    @Schema(description = "아바타 id")
    private Long avatarId;

    WorldAvatarResponseDto(Long worldAvatarId, Long worldId, Long avatarId) {
        this.worldAvatarId = worldAvatarId;
        this.worldId = worldId;
        this.avatarId = avatarId;
    }

    public static WorldAvatarResponseDto fromEntity(WorldAvatar worldAvatar) {
        return new WorldAvatarResponseDto(
                worldAvatar.getWorldAvatarId(),
                worldAvatar.getWorld().getWorldId(),
                worldAvatar.getAvatar().getAvatarId());
    }
}
