package cmc.dto.response;

import cmc.domain.WorldHashtag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class WorldHashtagResponseDto {
    @Schema(description = "세계관 해시태그 중간 테이블의 pk")
    private Long worldHashtagId;

    @Schema(description = "해시태그 id")
    private Long hashtagId;

    @Schema(description = "해시태그 이름")
    private String hashtagName;

    WorldHashtagResponseDto(Long worldHashtagId, Long hashtagId, String hashtagName) {
        this.worldHashtagId = worldHashtagId;
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
    }

    public static WorldHashtagResponseDto fromEntity (WorldHashtag worldHashtag) {
         return new WorldHashtagResponseDto(
                 worldHashtag.getWorldHashtagId(),
                 worldHashtag.getHashtag().getHashtagId(),
                 worldHashtag.getHashtag().getHashtagName());
    }
}
