package cmc.service;

import cmc.domain.User;
import cmc.domain.model.SocialType;
import cmc.dto.TokenDto;
import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import cmc.jwt.token.JwtProvider;
import cmc.jwt.token.JwtToken;
import cmc.repository.UserRepository;
import cmc.utils.KakaoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public TokenDto loginUser(String deviceToken, String socialId, SocialType socialType) {
        log.info("loginUser start");

        User savedUser = null;

        Optional<User> isExist = userRepository.findBySocialId(socialId);

        // 유저가 존재하지 않는다면 회원가입
        if(isExist.isEmpty()) {
            log.info("is empty");
            User user = User.builder()
                    .socialId(socialId)
                    .socialType(socialType)
                    .deviceToken(deviceToken)
                    .build();

            log.info("is empty save user");
            savedUser = userRepository.save(user);

        } else {
            log.info("is not empty");
            savedUser = isExist.get();

            // 다른 디바이스로 로그인한 경우를 위한 디바이스 토큰 업데이트
            savedUser.setDeviceToken(deviceToken);
        }

        log.info("분기 처리 밖");
        String userId = savedUser.getUserId().toString();

        String accessToken = jwtProvider.createAccessToken(userId).getToken();
        String refreshToken = jwtProvider.createRefreshToken(userId).getToken();

        savedUser.setRefreshToken(refreshToken);

        // 새로 발급된 리프레시 토큰과 업데이트된 디바이스 토큰 저장
        log.info("분기 처리 밖 save");
        userRepository.save(savedUser);

        TokenDto tokenDto = TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        log.info("loginUser end");

        return tokenDto;
    }

    public String reissueAccessToken(String refreshToken) {

        JwtToken jwtRefreshToken = jwtProvider.convertToJwtToken(refreshToken);
        String tokenUserId = jwtRefreshToken.getTokenClaims().getSubject();

        User user = userRepository.findById(Long.parseLong(tokenUserId))
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // refresh token의 유효성 확인
        if (user.getRefreshToken().equals(refreshToken) && jwtRefreshToken.validate()) {

            String accessToken = jwtProvider.createAccessToken(user.getUserId().toString()).getToken();

            return accessToken;
        } else {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_VALID);
        }
    }
}
