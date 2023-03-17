package cmc.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateWorldInfoRequestDto {
    @Schema(description = "세계관 이름", required = true)
    private String worldName;

    @Schema(description = "세계관 인원 제한", required = true)
    private Integer worldUserLimit;

    @Schema(description = "세계관 시작 날짜", required = true)
    private String worldStartDate;

    @Schema(description = "세계관 마감 날짜", required = true)
    private String worldEndDate;

    @Schema(description = "세계관 공지", required = true)
    private String worldNotice;

    @Schema(description = "세계관 비밀번호", required = true)
    private String worldPassword;

    @Schema(description = "세계관 방장 유저 id", required = true)
    private Long worldHostUserId;
}
