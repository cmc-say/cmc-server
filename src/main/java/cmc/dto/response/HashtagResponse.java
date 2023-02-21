package cmc.dto.response;

import cmc.domain.Hashtag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class HashtagResponse {
    private Long hashtagId;
    private String hashtagName;

    HashtagResponse(Long hashtagId, String hashtagName) {
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
    }

    public static HashtagResponse fromEntity (Hashtag hashtag) {
         return new HashtagResponse(
                 hashtag.getHashtagId(),
                 hashtag.getHashtagName());
    }
}
