package cmc.domain.User.service;

import cmc.domain.User.entity.Block;
import cmc.domain.User.entity.Report;
import cmc.domain.User.repository.UserBlockRepository;
import cmc.domain.User.repository.UserReportRepository;
import cmc.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserBlockRepository userBlockRepository;
    private UserReportRepository userReportRepository;

    @Transactional
    public void blockUser(Long userId) {

        Block block = Block.builder()
                .blockedUserId(userId)
                .blockingUserId(1L)
                .build();

        userBlockRepository.save(block);
    }

    @Transactional
    public void reportUser(Long userId) {
        Report report = Report.builder()
                .reportedUserId(userId)
                .reportingUserId(1L)
                .build();

        userReportRepository.save(report);
    }
}
