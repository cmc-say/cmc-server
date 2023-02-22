package cmc.dto.response;

import cmc.domain.WorldAvatar;
import lombok.Getter;

@Getter
public class WorldAvatarResponseDto {
    private Long worldAvatarId;
    private Long worldId;
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
