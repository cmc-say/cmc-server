package cmc.dto.response;

import java.util.Optional;

public interface AvatarsInWorldResponseDto {
    Long getAvatarId();
    String getAvatarName();
    String getAvatarImg();
    String getAvatarMessage();
    Long getUserId();
    Optional<Long> getWordtodayId();
}
