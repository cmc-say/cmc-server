package cmc.dto.response;

import cmc.domain.Hashtag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HashtagResponseDto {
    @Schema(description = "해시태그 id")
    private Long hashtagId;
    @Schema(description = "해시태그 이름")
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
