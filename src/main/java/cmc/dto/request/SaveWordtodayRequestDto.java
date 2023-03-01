package cmc.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SaveWordtodayRequestDto {

    @Schema(description = "오늘의 한마디 내용", example = "내용 예시", defaultValue = "내용")
    private String wordtodayContent;
}
