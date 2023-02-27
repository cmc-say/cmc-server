package cmc.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class IsMemberOfWorldResponseDto {
    @Schema(description = "유저의 세계관 참여 여부", nullable = false, example = "true")
    private Boolean isMember;

    public IsMemberOfWorldResponseDto(boolean isMember) {
        this.isMember = isMember;
    }
}
