package cmc.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateDeletedWorldHashtagsRequestDto {
    @ArraySchema(schema = @Schema(description = "삭제될 세계관 해시태그 매핑 id", defaultValue = "1", example = "1"))
    private List<Long> worldHashtagIds;
}
