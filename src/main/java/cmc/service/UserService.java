package cmc.service;


import cmc.domain.Avatar;
import cmc.domain.Block;
import cmc.domain.Report;
import cmc.domain.User;
import cmc.domain.model.ReportType;
import cmc.repository.AvatarRepository;
import cmc.repository.BlockRepository;
import cmc.repository.ReportRepository;
import cmc.repository.UserRepository;
import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final ReportRepository reportRepository;
    private final AvatarRepository avatarRepository;

    @Transactional
    public void blockUser(Long blockingUserId, Long blockedUserId) {

        checkDuplicatedBlock(blockingUserId, blockedUserId);
        checkSelf(blockingUserId, blockedUserId);

        User blockedUser = findUser(blockedUserId);

        Block block = Block.builder()
                .blockedUser(blockedUser)
                .blockingUserId(blockingUserId)
                .build();

        blockRepository.save(block);
    }

    @Transactional
    public void reportUser(Long reportingUserId, Long reportedUserId, ReportType reportType) {

        checkDuplicatedReport(reportingUserId, reportedUserId);
        checkSelf(reportingUserId, reportedUserId);

        User reportedUser = findUser(reportedUserId);

        Report report = Report.builder()
                .reportedUser(reportedUser)
                .reportingUserId(reportingUserId)
                .reportType(reportType)
                .build();

        reportRepository.save(report);

        checkAccumulatedReport(reportedUserId);
    }

    public User findUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    }

    public List<Avatar> getCharactersByUserId(Long userId) {
        return avatarRepository.findAvatarsByUserId(userId);
    }

    public boolean isMemberOfWorldByUserId(Long userId, Long worldId) {

        boolean isMemeber = !userRepository.findUserByUserIdAndWorldId(userId, worldId).isEmpty();

        return isMemeber;
    }

    private void checkDuplicatedBlock(Long blockingUserId, Long blockedUserId) {

        if(!blockRepository.findBlockByBlockingUserIdAndBlockedUser(blockingUserId, blockedUserId).isEmpty()){
            throw new BusinessException(ErrorCode.DUPLICATED_BLOCK);
        }

    }

    private void checkDuplicatedReport(Long reportingUserId, Long reportedUserId) {

        if(!reportRepository.findByReportingUserIdAndReportedUserId(reportingUserId, reportedUserId).isEmpty()){
            throw new BusinessException(ErrorCode.DUPLICATED_REPORT);
        }

    }

    private void checkSelf(Long userId, Long targetUserId) {

        if(userId == targetUserId) {
            throw new BusinessException(ErrorCode.SELF_BLOCK_OR_REPORT);
        }

    }

    // 신고 누적이 기준을 넘어서면 규칙에 맞게 캐릭터의 property 를 바꿔준다.
    private void checkAccumulatedReport(Long reportedUserId) {

        int REPORT_MAX_COUNT = 5;

        // 누적이 기준을 넘어선 경우 : user가 갖고 있는 캐릭터 모두가 정해진 캐릭터 이름, 캐릭터 상태메세지로 변경된다
        if(reportRepository.findReportByReportedUserId(reportedUserId).size() >= REPORT_MAX_COUNT) {
            List<Avatar> avatars = avatarRepository.findAvatarsByUserId(reportedUserId);
            avatarRepository.saveAll(avatars.stream().map(Avatar::changeToReportedAvatar).collect(Collectors.toList()));
        }

    }
}
