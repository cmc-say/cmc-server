package cmc.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateDeletedWorldHashtagsRequestDto {
    private List<String> worldHashtagIds;
}
