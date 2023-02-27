package cmc.dto.response;

import cmc.domain.Hashtag;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HashtagResponseDto {
    private Long hashtagId;
    private String hashtagName;

    @Builder
    HashtagResponseDto(Long hashtagId, String hashtagName) {
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
    }

    public static HashtagResponseDto fromEntity(Hashtag hashtag) {
        return HashtagResponseDto.builder()
                .hashtagId(hashtag.getHashtagId())
                .hashtagName(hashtag.getHashtagName())
                .build();
    }
}
