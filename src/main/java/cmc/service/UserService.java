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

import java.util.List;

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

        User blockedUser = this.findUser(blockedUserId);

        Block block = Block.builder()
                .blockedUser(blockedUser)
                .blockingUserId(blockingUserId)
                .build();

        userBlockRepository.save(block);
    }

    @Transactional
    public void reportUser(Long reportingUserId, Long reportedUserId, ReportType reportType) {

        checkDuplicatedReport(reportingUserId, reportedUserId);
        checkSelf(reportingUserId, reportedUserId);

        User reportedUser = this.findUser(reportedUserId);

        Report report = Report.builder()
                .reportedUser(reportedUser)
                .reportingUserId(reportingUserId)
                .reportType(reportType)
                .build();

        userReportRepository.save(report);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
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

    // 신고 누적이 기준을 넘어서면 캐릭터가
    private List<Report> checkAccumulatedReport(Long reportedUserId) {
        int REPORT_MAX_COUNT = 5;
        String BLOCKED_USER_NAME = "차단된 유저";
        String BLOCKED_USER_MESSAGE = "차단된 메세지";

//         누적이 기준을 넘어선 경우 : user가 갖고 있는 캐릭터 모두가 정해진 캐릭터 이름, 캐릭터 상태메세지로 변경된다
        if(userReportRepository.findReportByReportedUserId(reportedUserId).size() >= REPORT_MAX_COUNT) {

        }
    }
}
