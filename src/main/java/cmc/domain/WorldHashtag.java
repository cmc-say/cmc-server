package cmc.domain;

import cmc.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(
        name = "world_hashtag",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "worldId_hashtagId_unique",
                    columnNames = {"worldId", "hashtagId"}
            )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Setter
@Entity
public class WorldHashtag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long worldHashtagId;

    @ManyToOne
    @JoinColumn(name = "worldId")
    private World world;

    @ManyToOne
    @JoinColumn(name = "hashtagId")
    @JsonBackReference
    private Hashtag hashtag;

    @Builder
    public WorldHashtag(Long worldHashtagId, World world, Hashtag hashtag) {
        this.worldHashtagId = worldHashtagId;
        this.world = world;
        this.hashtag = hashtag;
    }
}
