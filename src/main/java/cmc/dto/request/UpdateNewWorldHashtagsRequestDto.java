package cmc.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateNewWorldHashtagsRequestDto {
    @ArraySchema(schema = @Schema(description = "새로 생성된 해시태그 이름", defaultValue = "해시태그1", example = "해시태그1"))
    private List<String> hashtags;
}
