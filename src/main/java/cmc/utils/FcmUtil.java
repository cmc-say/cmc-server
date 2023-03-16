package cmc.utils;

import cmc.dto.request.FcmMessage;
import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class FcmUtil {

    @Value("${fcm.key.path}")
    private String FCM_KEY_PATH;

    @Value("${fcm.key.api-url}")
    private String API_URL;

    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, FcmMessage.Notification notification, FcmMessage.Data data) {
        String message = makeMessage(targetToken, notification, data);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        try {
            Response response  = client.newCall(request).execute();
            log.info("FCM 알림 보내기 성공 {}", response.body().string());
        } catch (IOException e) {
            log.info("FCM REQUEST FAILED {}", e);
            throw new BusinessException(ErrorCode.FCM_REQUEST_FAILED);
        }
    }

    private String makeMessage(String targetToken, FcmMessage.Notification notification, FcmMessage.Data data) {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(notification.getTitle())
                                .body(notification.getBody())
                                .image(notification.getImage())
                                .build()
                        )
                        .data(FcmMessage.Data.builder()
                                .recieverAvatarName(data.getRecieverAvatarName())
                                .senderAvatarName(data.getSenderAvatarName())
                                .senderAvatarImg(data.getSenderAvatarImg())
                                .alarmMessage(data.getAlarmMessage())
                                .build())
                        .build()
                )
                .validate_only(false)
                .build();

        try {
            return objectMapper.writeValueAsString(fcmMessage);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.MAKE_FCM_MESSAGE_FAILED);
        }
    }

    private String getAccessToken() {
        // accessToken 생성
        try {
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(FCM_KEY_PATH).getInputStream())
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            googleCredentials.refreshIfExpired();

            // GoogleCredential의 getAccessToken으로 토큰 받아온 뒤, getTokenValue로 최종적으로 받음
            // REST API로 FCM에 push 요청 보낼 때 Header에 설정하여 인증을 위해 사용
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e) {
            log.info("FCM ACCESS TOKEN FAILED {}", e);
            throw new BusinessException(ErrorCode.FCM_ACCESS_TOKEN_FAILED);
        }
    }
}
