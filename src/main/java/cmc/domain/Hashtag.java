package cmc.domain;

import cmc.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(
        name = "Hashtag",
        indexes = {
                @Index(columnList = "hashtagName")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Hashtag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    @Column(nullable = false, length = 20)
    private String hashtagName;

    @Builder
    public Hashtag(Long hashtagId, String hashtagName) {
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
    }
}
