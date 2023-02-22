package cmc.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class SaveWorldRequestDto {
    private String worldName;
    private Integer worldUserLimit;
    private List<String> hashtags;
    private String worldPassword;
    private String worldNotice;
    private List<String> todos;
}
