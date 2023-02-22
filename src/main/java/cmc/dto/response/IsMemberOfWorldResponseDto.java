package cmc.dto.response;

import lombok.Getter;

@Getter
public class IsMemberOfWorldResponseDto {
    private Boolean isMember;

    public IsMemberOfWorldResponseDto(boolean isMember) {
        this.isMember = isMember;
    }
}
