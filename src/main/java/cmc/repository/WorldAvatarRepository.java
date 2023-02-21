package cmc.repository;

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
    List<World> findWorldWithHashtag(@Param("avatarId") Long avatarId);
}
