package cmc.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SaveAvatarRequestDto {

    @Schema(description = "아바타 이름", defaultValue = "아바타 이름 예시", example = "아바타 이름 예시")
    private String avatarName;

    @Schema(description = "아바타 상태 메세지", defaultValue = "아바타 상태 메세지 예시", example = "아바타 상태 메세지 예시")
    private String avatarMessage;

    @Schema(description = "아바타 이미지")
    private MultipartFile avatarImg;

}
