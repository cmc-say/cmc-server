package cmc.domain;

import cmc.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "wordtoday")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Wordtoday extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordtodayId;

    @ManyToOne
    @JoinColumn(name = "worldAvatarId")
    private WorldAvatar worldAvatar;

    @Setter
    @Column(length = 300)
    private String wordtodayContent;

    @Builder
    public Wordtoday(Long wordtodayId, WorldAvatar worldAvatar, String wordtodayContent) {
        this.wordtodayId = wordtodayId;
        this.worldAvatar = worldAvatar;
        this.wordtodayContent = wordtodayContent;
    }

}
