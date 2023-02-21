package cmc.domain.world.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class SaveWorldRequest {
    private String worldName;
    private Integer worldUserLimit;
    private List<String> hashtags;
    private String worldPassword;
    private String worldNotice;
    private List<String> todos;
}
