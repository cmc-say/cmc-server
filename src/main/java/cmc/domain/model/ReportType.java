package cmc.domain.model;

import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public enum ReportType {
    WORLD("world"), AVATAR_NAME("avatarName"), AVATAR_MESSAGE("avatarMessage");

    private final String reportTypeName;

    ReportType(String reportTypeName) {
        this.reportTypeName = reportTypeName;
    }

    public static ReportType fromString(String reportTypeName) {
        return Arrays.stream(ReportType.values())
                .filter(r -> r.reportTypeName.equals(reportTypeName))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.REPORT_TYPE_ERROR));
    }
}
