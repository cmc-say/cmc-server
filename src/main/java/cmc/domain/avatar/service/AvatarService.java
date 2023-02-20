package cmc.domain.avatar.service;

import cmc.domain.avatar.entity.Avatar;
import cmc.domain.avatar.repository.AvatarRepository;
import cmc.domain.user.entity.User;
import cmc.domain.user.service.UserService;
import cmc.global.error.exception.BusinessException;
import cmc.global.error.exception.ErrorCode;
import cmc.global.utils.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final UserService userService;
    private final S3Util s3Util;

    public List<Avatar> getCharactersByUserId(Long userId) {
        return avatarRepository.findAllByUser(userId);
    }

    @Transactional
    public void saveAvatar(Long tokenUserId, String avatarName, String avatarMessage, MultipartFile file) {
        String imgUrl = s3Util.upload(file, "character");
        User user = userService.findUser(tokenUserId);
        log.info("img url {}", imgUrl.length());

        Avatar avatar = Avatar.builder()
                .avatarName(avatarName)
                .avatarMessage(avatarMessage)
                .avatarImg(imgUrl)
                .user(user)
                .build();
        avatarRepository.save(avatar);
        return;
    }
}
