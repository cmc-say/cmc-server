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

    private final UserRepository userRepository;
    private final UserBlockRepository userBlockRepository;
    private final UserReportRepository userReportRepository;

    @Transactional
    public void blockUser(Long blockingUserId, Long blockedUserId) {

        Block block = Block.builder()
                .blockedUserId(blockedUserId)
                .blockingUserId(blockingUserId)
                .build();

        userBlockRepository.save(block);
    }

    @Transactional
    public void reportUser(Long reportingUserId, Long reportedUserId) {
        Report report = Report.builder()
                .reportedUserId(reportedUserId)
                .reportingUserId(reportingUserId)
                .build();

        userReportRepository.save(report);
    }
}
