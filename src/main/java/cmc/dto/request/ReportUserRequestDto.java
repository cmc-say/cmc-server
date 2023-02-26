package cmc.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReportUserRequestDto {

    @Schema(description = "신고 타입 world, avatarName, avatar Message 중 하나", required = true, defaultValue = "world", example = "world")
    private String reportType;

}
