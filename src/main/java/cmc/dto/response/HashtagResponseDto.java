package cmc.dto.response;

import cmc.domain.Hashtag;
import lombok.Getter;

@Getter
public class HashtagResponseDto {
    private Long hashtagId;
    private String hashtagName;

    HashtagResponseDto(Long hashtagId, String hashtagName) {
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
    }

    public static HashtagResponseDto fromEntity (Hashtag hashtag) {
         return new HashtagResponseDto(
                 hashtag.getHashtagId(),
                 hashtag.getHashtagName());
    }
}
