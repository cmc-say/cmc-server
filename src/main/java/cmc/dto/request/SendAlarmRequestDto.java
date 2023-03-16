package cmc.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SendAlarmRequestDto {
    @Schema(description = "전송하는 아바타 id")
    private Long senderAvatarId;

    @Schema(description = "수신하는 아바타 id")
    private Long receiverAvatarId;

    @Schema(description = "전송하는 메세지")
    private String alarmMessage;
}
