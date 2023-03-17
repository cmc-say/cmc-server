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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final KakaoUtil kakaoUtil;
    private final JwtProvider jwtProvider;

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public TokenDto loginUser(String deviceToken, String socialId, SocialType socialType) {

        if(socialType == SocialType.KAKAO) {

//            String socialAccessToken = kakaoUtil.getAccessToken(authorizationCode);
//            String socialId = kakaoUtil.getSocialId(socialAccessToken);

            User savedUser = null;

            Optional<User> isExist = userRepository.findBySocialId(socialId);

            // 유저가 존재하지 않는다면 회원가입
            if(isExist.isEmpty()) {
                User user = User.builder()
                        .socialId(socialId)
                        .socialType(socialType)
                        .deviceToken(deviceToken)
                        .build();

                savedUser = userRepository.save(user);

            } else {
                savedUser = isExist.get();
            }

            String userId = savedUser.getUserId().toString();

            String accessToken = jwtProvider.createAccessToken(userId).getToken();
            String refreshToken = jwtProvider.createRefreshToken(userId).getToken();

            savedUser.setRefreshToken(refreshToken);

            userRepository.save(savedUser);

            TokenDto tokenDto = TokenDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            return tokenDto;
        }

        else if (socialType.equals(SocialType.APPLE)) {


        }
        TokenDto tokenDto = TokenDto.builder()
                .accessToken("temp")
                .refreshToken("temp")
                .build();

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
