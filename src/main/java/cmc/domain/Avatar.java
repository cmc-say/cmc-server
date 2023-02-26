package cmc.domain;

import cmc.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "avatar", indexes = {@Index(columnList = "userId")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Avatar extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatarId;

    @Setter
    @Column(length = 12)
    private String avatarName;

    @Setter
    @Column(length = 30)
    private String avatarMessage;

    @Setter
    @Column(length = 300)
    private String avatarImg;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(targetEntity = WorldAvatar.class, fetch = FetchType.LAZY, mappedBy = "avatar", cascade = CascadeType.ALL)
    private List<WorldAvatar> worldAvatars = new ArrayList<>();

    @Builder
    public Avatar(Long avatarId, String avatarName, String avatarMessage, String avatarImg, User user) {
        this.avatarId = avatarId;
        this.avatarName = avatarName;
        this.avatarMessage = avatarMessage;
        this.avatarImg = avatarImg;
        this.user = user;
    }

    // 신고 기준치를 넘은 유저의 캐릭터들을 규칙에 맞게 바꿔주는 함수
    public Avatar changeToReportedAvatar() {
        String BLOCKED_AVATAR_NAME = "차단된 캐릭터";
        String BLOCKED_AVATAR_MESSAGE = "차단된 메세지";

        this.setAvatarName(BLOCKED_AVATAR_NAME);
        this.setAvatarMessage(BLOCKED_AVATAR_MESSAGE);

        return this;
    }
}
