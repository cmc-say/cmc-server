package cmc.domain.avatar.repository;

import cmc.domain.avatar.entity.WorldAvatar;
import cmc.domain.world.entity.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorldAvatarRepository extends JpaRepository<WorldAvatar, Long> {

    @Query(value = "SELECT w " +
            "FROM WorldAvatar wa " +
            "JOIN wa.world w " +
            "JOIN w.worldHashtags wh " +
            "JOIN wh.hashtag " +
            "WHERE wa.avatar.avatarId = :avatarId")
    List<World> findWorldWithHashtag(@Param("avatarId") Long avatarId);
}
