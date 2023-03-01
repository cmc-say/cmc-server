package cmc.dto.response;

import cmc.domain.Avatar;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarResponseDto {
    @Schema(description = "아바타 id", nullable = false, example = "1")
    private Long avatarId;

    @Schema(description = "아바타 이름", nullable = false, example = "피카츄")

    private String avatarName;

    @Schema(description = "아바타 이미지", example = "이미지 url")
    private String avatarImg;

    @Schema(description = "아바타 상태 메세지", example = "배고파요")
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
