package cmc.service;

import cmc.domain.User;
import cmc.domain.model.SocialType;
import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import cmc.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${kakao.client-id}")
    private final String KAKAO_CLIENT_ID;

    @Value("${kakao.redirect-uri}")
    private final String KAKAO_REDIRECT_URI;

    private final UserRepository userRepository;

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void loginUser(String deviceToken, String authorizationCode, SocialType socialType) {

        if(socialType.equals(SocialType.KAKAO)) {

            String socialId = null;
            try {
                socialId = getSocialIdByKakao(authorizationCode);
            } catch (JsonProcessingException e ) {
                throw new BusinessException(ErrorCode.SOCIAL_LOGIN_FAIL);
            }

            Optional<User> isExists = userRepository.findBySocialId(socialId);

            // 유저가 존재하지 않는다면 회원가입
            if(isExists.isEmpty()) {
                User user = User.builder()
                        .socialId(socialId)
                        .socialType(socialType)
                        .refreshToken("tempory refresh token")
                        .deviceToken("temporary device token")
                        .build();

                userRepository.save(user);
            }
        }

        else if (socialType.equals(SocialType.APPLE)) {

        }
    }

    private String getSocialIdByKakao(String authorizationCode) throws JsonProcessingException {
        // 인가코드로 어세스 토큰 발급
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", KAKAO_CLIENT_ID);
        body.add("redirect_uri", KAKAO_REDIRECT_URI);
        body.add("code", authorizationCode);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String accessToken =  jsonNode.get("access_token").asText();

        // 어세스 토큰으로 소셜 id 받기
        // HTTP Header 생성
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        rt = new RestTemplate();
        response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        responseBody = response.getBody();
        objectMapper = new ObjectMapper();
        jsonNode = objectMapper.readTree(responseBody);

        String socialId = jsonNode.get("id").asText();

        return socialId;
    }
}
