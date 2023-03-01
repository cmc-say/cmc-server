package cmc.repository;

import cmc.domain.User;
import cmc.domain.WorldAvatar;
import cmc.domain.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorldAvatarRepository extends JpaRepository<WorldAvatar, Long> {
    @Query(value = "SELECT wa FROM WorldAvatar wa " +
            "WHERE wa.world.worldId = :worldId " +
            "AND wa.avatar.avatarId = :avatarId ")
    Optional<WorldAvatar> findByAvatarIdAndWorldId(@Param("avatarId") Long avatarId, @Param("worldId") Long worldId);
}
