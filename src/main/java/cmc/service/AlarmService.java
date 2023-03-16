package cmc.service;

import cmc.domain.Avatar;
import cmc.domain.RecommendedAlarm;
import cmc.dto.request.FcmMessage;
import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import cmc.repository.AvatarRepository;
import cmc.repository.RecommendedAlarmRepository;
import cmc.utils.FcmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final RecommendedAlarmRepository recommendedAlarmRepository;
    private final AvatarRepository avatarRepository;
    private final FcmUtil fcmUtil;

    public List<RecommendedAlarm> getRecommendedAlarm() {
        return recommendedAlarmRepository.findAll();
    }

    public void sendAlarm(Long receiverAvatarId, Long senderAvatarId, String alarmMessage) {

        String notificationTitle = "누군가 당신의 캐릭터를 깨웠어요";

        Avatar receiverAvatar = avatarRepository.findById(receiverAvatarId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AVATAR_NOT_FOUND));

        Avatar senderAvatar = avatarRepository.findById(senderAvatarId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AVATAR_NOT_FOUND));

        String receiverDeviceToken = receiverAvatar.getUser().getDeviceToken();

        FcmMessage.Notification notification = FcmMessage.Notification.builder()
                .title(notificationTitle)
                .body(alarmMessage)
                .image(null) // 이미지 리소스에 넣기
                .build();

        FcmMessage.Data data = FcmMessage.Data.builder()
                .recieverAvatarName(receiverAvatar.getAvatarName())
                .senderAvatarName(senderAvatar.getAvatarName())
                .senderAvatarImg(senderAvatar.getAvatarImg())
                .alarmMessage(alarmMessage)
                .build();


        fcmUtil.sendMessageTo(receiverDeviceToken, notification, data);
    }
}
