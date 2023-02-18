package cmc.domain.User.entity;

import cmc.domain.User.model.SocialType;
import cmc.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "User", indexes = {
        @Index(columnList = "refreshToken"),
        @Index(columnList = "deviceToken")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Setter @Column(nullable = false, length = 50) private Long socialId;
    @Setter @Column(nullable = false, length = 10) private SocialType socialType;
    @Setter @Column(length = 100) private String refreshToken;
    @Setter @Column(length= 100) private String deviceToken;

    @Builder
    public User(Long userId, Long socialId, SocialType socialType, String refreshToken, String deviceToken) {
        this.userId = userId;
        this.socialId = socialId;
        this.socialType = socialType;
        this.refreshToken = refreshToken;
        this.deviceToken = deviceToken;
    }

}