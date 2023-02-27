package cmc.dto.response;

import cmc.domain.WorldHashtag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class HashtagResponseDto {
    @Schema(description = "세계관 해시태그 중간 테이블의 pk")
    private Long worldHashtagId;

    @Schema(description = "해시태그 id")
    private Long hashtagId;

    @Schema(description = "해시태그 이름")
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
