package cmc.domain.avatar.entity;

import cmc.domain.world.entity.World;
import cmc.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "WorldAvatar", indexes = {@Index(columnList = "worldId"), @Index(columnList = "avatarId")})
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

    @Builder
    public WorldAvatar(Long worldAvatarId, World world, Avatar avatar) {
        this.worldAvatarId = worldAvatarId;
        this.world = world;
        this.avatar = avatar;
    }
}