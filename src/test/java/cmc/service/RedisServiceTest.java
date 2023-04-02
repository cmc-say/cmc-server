package cmc.service;

import cmc.TestSupport;
import cmc.domain.User;
import cmc.domain.model.SocialType;
import cmc.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("회원가입 중복요청 테스트")
@SpringBootTest
public class RedisServiceTest extends TestSupport {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 같은_socialId_요청() throws Exception {
        // given
        int threadCount = 2;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        IntStream.range(0, threadCount).forEach(e -> executorService.submit(() -> {
                    try {
                        authService.loginUser("deviceToken", "tempSocialId", SocialType.KAKAO);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
        ));

        countDownLatch.await();

        // then
        int count = userRepository.findAll().size();
        assertThat(count).isEqualTo(1);

        // 테스트 후처리
        List<User> all = userRepository.findAll();
        for(User u : all) {
            System.out.println("existing user " + u.getUserId());
        }

        userRepository.deleteAll();

    }

    @Test
    void 다른_socialId_요청() throws Exception {
        // given
        int threadCount = 2;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        IntStream.range(0, threadCount).forEach(e -> executorService.submit(() -> {
                    try {
                        authService.loginUser("deviceToken", "tempSocialId"+e, SocialType.KAKAO);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
        ));

        countDownLatch.await();

        // then
        int count = userRepository.findAll().size();
        assertThat(count).isEqualTo(threadCount);

        // 테스트 후처리
        List<User> all = userRepository.findAll();
        for(User u : all) {
            System.out.println("existing user " + u.getUserId());
        }

        userRepository.deleteAll();

    }
}
