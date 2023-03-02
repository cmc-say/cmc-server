package cmc.domain;

import cmc.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Table(name = "world_avatar", indexes = {@Index(columnList = "worldId"), @Index(columnList = "avatarId")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class WorldAvatar extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long worldAvatarId;

    @ManyToOne
    @JoinColumn(name = "worldId")
    private World world;

    @ManyToOne
    @JoinColumn(name = "avatarId")
    private Avatar avatar;

    @OneToMany(targetEntity = Wordtoday.class, fetch = FetchType.LAZY, mappedBy = "worldAvatar", cascade = CascadeType.ALL)
    private List<Wordtoday> wordtodays;

    @OneToMany(targetEntity = CheckedTodo.class, fetch = FetchType.LAZY, mappedBy = "worldAvatar", cascade = CascadeType.ALL)
    private List<CheckedTodo> checkedTodos;

    @Builder
    public WorldAvatar(Long worldAvatarId, World world, Avatar avatar) {
        this.worldAvatarId = worldAvatarId;
        this.world = world;
        this.avatar = avatar;
    }
}
