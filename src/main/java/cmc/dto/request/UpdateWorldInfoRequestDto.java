package cmc.dto.request;

import lombok.Getter;

@Getter
public class UpdateWorldInfoRequestDto {
    private String worldName;
    private Integer worldUserLimit;
    private String worldStartDate;
    private String worldEndDate;
    private String worldNotice;
    private String worldPassword;
    private Long worldHostUserId;
}
