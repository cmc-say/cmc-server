package cmc.dto.response;

import lombok.Getter;

@Getter
public class IsMemberOfWorldResponse {
    private Boolean isMember;

    public IsMemberOfWorldResponse(boolean isMember) {
        this.isMember = isMember;
    }
}
