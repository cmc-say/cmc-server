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
    @JoinColumn(name = "avatarId")
    private Avatar avatar;

    @ManyToOne
    @JoinColumn(name = "worldId")
    private World world;

    @Setter
    @Column(length = 300)
    private String wordtodayContent;

    @Builder
    public Wordtoday(Long wordtodayId, World world, Avatar avatar, String wordtodayContent) {
        this.wordtodayId = wordtodayId;
        this.avatar = avatar;
        this.world = world;
        this.wordtodayContent = wordtodayContent;
    }

}
