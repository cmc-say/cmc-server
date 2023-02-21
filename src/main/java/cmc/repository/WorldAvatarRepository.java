package cmc.repository;

import cmc.domain.User;
import cmc.domain.WorldAvatar;
import cmc.domain.World;
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
    List<World> findWorldWithAvatar(@Param("avatarId") Long avatarId);

    @Query(value = "SELECT u FROM WorldAvatar wa " +
            "JOIN wa.avatar a " +
            "JOIN a.user u " +
            "WHERE u.userId = :userId " +
            "AND wa.world.worldId = :worldId ")
    List<User> findWorldByUserId(@Param("userId") Long userId, @Param("worldId") Long worldId);
}
