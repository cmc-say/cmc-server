package cmc.dto.response;

import cmc.domain.Avatar;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarResponseDto {
    private Long avatarId;
    private String avatarName;
    private String avatarImg;
    private String avatarMessage;

    @Builder
    public AvatarResponseDto(Long avatarId, String avatarName, String avatarImg, String avatarMessage) {
        this.avatarId = avatarId;
        this.avatarName = avatarName;
        this.avatarImg = avatarImg;
        this.avatarMessage = avatarMessage;
    }

    public static AvatarResponseDto fromEntity (Avatar avatar) {
        return AvatarResponseDto
                .builder()
                .avatarId(avatar.getAvatarId())
                .avatarName(avatar.getAvatarName())
                .avatarImg(avatar.getAvatarImg())
                .avatarMessage(avatar.getAvatarMessage())
                .build();
    }
}
