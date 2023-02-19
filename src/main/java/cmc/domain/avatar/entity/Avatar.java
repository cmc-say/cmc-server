package cmc.domain.avatar.entity;

import cmc.domain.user.entity.User;
import cmc.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "Avatar", indexes = {@Index(columnList = "userId")})
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
    @Column(length = 200)
    private String avatarImg;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public Avatar(Long avatarId, String avatarName, String avatarMessage, String avatarImg, User user) {
        this.avatarId = avatarId;
        this.avatarName = avatarName;
        this.avatarMessage = avatarMessage;
        this.avatarImg = avatarImg;
        this.user = user;
    }
}
