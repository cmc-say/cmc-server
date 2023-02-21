package cmc.dto.response;

import cmc.domain.Avatar;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SaveAvatarResponse {
    private Long avatarId;
    private String avatarName;
    private String avatarImg;
    private String avatarMessage;

    @Builder
    public SaveAvatarResponse(Long avatarId, String avatarName, String avatarImg, String avatarMessage) {
        this.avatarId = avatarId;
        this.avatarName = avatarName;
        this.avatarImg = avatarImg;
        this.avatarMessage = avatarMessage;
    }

    public static SaveAvatarResponse fromEntity (Avatar avatar) {
        return new SaveAvatarResponse(avatar.getAvatarId(), avatar.getAvatarName(), avatar.getAvatarImg(), avatar.getAvatarMessage());
    }
}
