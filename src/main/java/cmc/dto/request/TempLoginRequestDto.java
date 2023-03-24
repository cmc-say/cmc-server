package cmc.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class TempLoginRequestDto {

    @Schema(description = "socialId라고 되어있지만 아바타 이름 보내주세요")
    private String socialId;

}
