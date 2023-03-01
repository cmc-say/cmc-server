package cmc.dto.response;

import cmc.domain.Wordtoday;
import cmc.repository.WordtodayRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class WordtodayResponseDto {
    @Schema(description = "오늘의 한마디 id")
    private Long wordtodayId;
    @Schema(description = "오늘의 한마디 내용")
    private String wordtodayContent;

    @Schema(description = "캐릭터 id")
    private Long avatarId;

    WordtodayResponseDto(Long wordtodayId, String wordtodayContent, Long avatarId) {
        this.wordtodayId = wordtodayId;
        this.wordtodayContent = wordtodayContent;
        this.avatarId = avatarId;
    }

    public static WordtodayResponseDto fromEntity(Wordtoday wordtoday) {
        return new WordtodayResponseDto(
                wordtoday.getWordtodayId(),
                wordtoday.getWordtodayContent(),
                wordtoday.getWorldAvatar().getAvatar().getAvatarId()
        );
    }
}
