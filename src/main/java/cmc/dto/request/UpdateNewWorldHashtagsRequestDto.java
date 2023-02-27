package cmc.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateNewWorldHashtagsRequestDto {
    private List<String> hashtags;
}
