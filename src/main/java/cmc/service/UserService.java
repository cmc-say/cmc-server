package cmc.service;


import cmc.domain.Block;
import cmc.domain.Report;
import cmc.domain.User;
import cmc.domain.model.ReportType;
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

        checkDuplicatedBlock(blockingUserId, blockedUserId);
        checkSelf(blockingUserId, blockedUserId);

        Block block = Block.builder()
                .blockedUserId(blockedUserId)
                .blockingUserId(blockingUserId)
                .build();

        userBlockRepository.save(block);
    }

    @Transactional
    public void reportUser(Long reportingUserId, Long reportedUserId, ReportType reportType) {

        checkDuplicatedReport(reportingUserId, reportedUserId);
        checkSelf(reportingUserId, reportedUserId);

        Report report = Report.builder()
                .reportedUserId(reportedUserId)
                .reportingUserId(reportingUserId)
                .reportType(reportType)
                .build();

        userReportRepository.save(report);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private void checkDuplicatedBlock(Long blockingUserId, Long blockedUserId) {
        if(!userBlockRepository.findBlockByBlockingUserIdAndBlockedUserId(blockingUserId, blockedUserId).isEmpty()){
            throw new BusinessException(ErrorCode.DUPLICATED_BLOCK);
        }
    }

    private void checkDuplicatedReport(Long reportingUserId, Long reportedUserId) {
        if(!userReportRepository.findReportByReportingUserIdAndReportedUserId(reportingUserId, reportedUserId).isEmpty()){
            throw new BusinessException(ErrorCode.DUPLICATED_REPORT);
        }
    }

    private void checkSelf(Long userId, Long targetUserId) {
        if(userId == targetUserId) {
            throw new BusinessException(ErrorCode.SELF_BLOCK_OR_REPORT);
        }
    }
}
