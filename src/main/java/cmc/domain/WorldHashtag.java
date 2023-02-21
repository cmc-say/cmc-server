package cmc.domain;

import cmc.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(
        name = "World_Hashtag",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "worldId_hashtagId_unique",
                    columnNames = {"worldId", "hashtagId"}
            )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
