package cmc.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class SaveWorldRequestDto {
    @Schema(description = "세계관 이름", required = true, defaultValue = "해리포터 세계관", example = "해리포터 세계관")
    private String worldName;

    @Schema(description = "세계관 제한 인원", required = true, defaultValue = "10", example = "10")
    private Integer worldUserLimit;

    @ArraySchema(schema = @Schema(description = "세계관의 해시태그", defaultValue = "뉴진스", example = "뉴진스"))
    private List<String> hashtags;

    @Schema(description = "세계관 비밀번호", defaultValue = "123", example = "123")
    private String worldPassword;

    @Schema(description = "세계관 공지", defaultValue = "세계관의 공지입니다", example = "세계관의 공지입니다")
    private String worldNotice;

    @ArraySchema(schema = @Schema(description = "세계관의 todo", defaultValue = "삼시세끼 챙겨먹기", example =  "삼시세끼 챙겨먹기" ))
    private List<String> todos;

    @Schema(description = "세계관 이미지")
    private MultipartFile worldImg;
}
