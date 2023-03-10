package cmc.controller;

import cmc.common.ResponseCode;
import cmc.common.ResponseDto;
import cmc.domain.RecommendedAlarm;
import cmc.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
