package cmc.dto.response;

import cmc.domain.Avatar;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarResponseDto {
    @Schema(description = "아바타 id", nullable = false, example = "1")
    private Long avatarId;

    @Schema(description = "아바타 이름", nullable = false, example = "캐릭터 이름")

    private String avatarName;

    @Schema(description = "아바타 이미지", example = "이미지 url")
    private String avatarImg;

    @Schema(description = "아바타 상태 메세지", example = "캐릭터 상태 메세지")
    private String avatarMessage;

    @Schema(description = "유저 id")
    private Long userId;

    @Builder
    public AvatarResponseDto(Long avatarId, String avatarName, String avatarImg, String avatarMessage, Long userId ) {
        this.avatarId = avatarId;
        this.avatarName = avatarName;
        this.avatarImg = avatarImg;
        this.avatarMessage = avatarMessage;
        this.userId = userId;
    }

    public static AvatarResponseDto fromEntity (Avatar avatar) {
        return AvatarResponseDto
                .builder()
                .avatarId(avatar.getAvatarId())
                .avatarName(avatar.getAvatarName())
                .avatarImg(avatar.getAvatarImg())
                .avatarMessage(avatar.getAvatarMessage())
                .userId(avatar.getUser().getUserId())
                .build();
    }
}
