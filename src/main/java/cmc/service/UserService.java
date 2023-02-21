package cmc.service;


import cmc.domain.Block;
import cmc.domain.Report;
import cmc.domain.User;
import cmc.repository.UserBlockRepository;
import cmc.repository.UserReportRepository;
import cmc.repository.UserRepository;
import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
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

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

}
