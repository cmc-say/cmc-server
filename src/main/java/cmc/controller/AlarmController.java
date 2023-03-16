package cmc.controller;

import cmc.common.ResponseCode;
import cmc.common.ResponseDto;
import cmc.domain.Avatar;
import cmc.domain.RecommendedAlarm;
import cmc.dto.request.SendAlarmRequestDto;
import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import cmc.repository.AvatarRepository;
import cmc.service.AlarmService;
import cmc.utils.FcmUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/alarm")
@Tag(name = "Alarm 컨트롤러")
public class AlarmController {
    private final AlarmService alarmService;

    @Operation(
            summary = "추천 알림 조회"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추천 알림 조회")
    })
    @GetMapping("/recommended")
    public ResponseEntity<ResponseDto<List<RecommendedAlarm>>> getRecommendedAlarm() {

        List<RecommendedAlarm> recommendedAlarms = alarmService.getRecommendedAlarm();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(ResponseCode.RECOMMENDED_ALARM_FOUND, recommendedAlarms));
    }

    @Operation(
            summary = "알림 보내기",
            description = "인텐트 부가 정보" +
                    "\t\n { data : " +
                    "\t\n {" +
                    "\t\n   \"recieverAvatarName\": \"수신 아바타 이름\"," +
                    "\t\n   \"senderAvatarName\": \"송신 아바타 이름\"," +
                    "\t\n   \"senderAvatarImg\": \"img url\"," +
                    "\t\n   \"alarmMessage\": \"알림메세지 예시\" " +
                    "\t\n }" +
                    "\t\n }"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "알림 보내기")
    })
    @PostMapping("/fcm")
    public ResponseEntity<ResponseDto> sendAlarm(
            @RequestBody SendAlarmRequestDto req
            ) {

        alarmService.sendAlarm(req.getReceiverAvatarId(), req.getSenderAvatarId(), req.getAlarmMessage());

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto<>(ResponseCode.FCM_NOTIFICATION_SUCCESS));
    }
}
