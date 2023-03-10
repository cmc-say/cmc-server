package cmc.repository;

import cmc.domain.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    @Query(value = "SELECT a FROM Avatar a JOIN User u WHERE u.userId = :userId")
    List<Avatar> findAvatarsByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT a FROM WorldAvatar wa " +
            "JOIN wa.avatar a " +
            "JOIN a.user u " +
            "LEFT JOIN u.blockedUsers bu ON bu.blockedUser.userId = a.user.userId " +
            "WHERE wa.world.worldId = :worldId " +
            "AND (bu.blockingUserId IS NULL OR bu.blockingUserId <> :userId)")
    List<Avatar> getAvatarsByWorldIdWithoutBlockedUser(@Param("userId") Long userId, @Param("worldId") Long worldId);
}
