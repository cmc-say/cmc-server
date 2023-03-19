package cmc.domain.model;

import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public enum SocialType {
    KAKAO("kakao"), APPLE("apple"), GOOGLE("google");

    private final String socialTypeName;

    SocialType(String socialTypeName) {
        this.socialTypeName = socialTypeName;
    }

    public static SocialType fromString(String socialTypeName) {
        return Arrays.stream(SocialType.values())
                .filter(s -> s.socialTypeName.equals(socialTypeName))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.SOCIAL_TYPE_ERROR));
    }
}

