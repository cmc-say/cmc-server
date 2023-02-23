package cmc.domain;

import cmc.domain.model.SocialType;
import cmc.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "user", indexes = {
        @Index(columnList = "refreshToken"),
        @Index(columnList = "deviceToken")
},
uniqueConstraints =
        {@UniqueConstraint(columnNames = "userId")}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Setter @Column(nullable = false, length = 50) private Long socialId;
    @Enumerated(EnumType.STRING) @Setter @Column(nullable = false, length = 10) private SocialType socialType;
    @Setter @Column(length = 100) private String refreshToken;
    @Setter @Column(length= 100) private String deviceToken;

    @OneToMany(targetEntity = Avatar.class, fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Avatar> avatars = new ArrayList<>();

    @Builder
    public User(Long userId, Long socialId, SocialType socialType, String refreshToken, String deviceToken) {
        this.userId = userId;
        this.socialId = socialId;
        this.socialType = socialType;
        this.refreshToken = refreshToken;
        this.deviceToken = deviceToken;
    }

}