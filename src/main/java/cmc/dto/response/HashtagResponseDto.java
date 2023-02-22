package cmc.dto.response;

import cmc.domain.WorldHashtag;
import lombok.Getter;

@Getter
public class HashtagResponseDto {
    private Long worldHashtagId;

    private Long hashtagId;
    private String hashtagName;

    HashtagResponseDto(Long hashtagId, Long worldHashtagId, String hashtagName) {
        this.worldHashtagId = worldHashtagId;
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
    }

    public static HashtagResponseDto fromEntity (WorldHashtag worldHashtag) {
         return new HashtagResponseDto(
                 worldHashtag.getWorldHashtagId(),
                 worldHashtag.getHashtag().getHashtagId(),
                 worldHashtag.getHashtag().getHashtagName());
    }
}
