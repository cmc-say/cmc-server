package cmc.repository;

import cmc.domain.Avatar;
import cmc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM WorldAvatar wa " +
            "JOIN wa.avatar a " +
            "JOIN a.user u " +
            "WHERE u.userId = :userId " +
            "AND wa.world.worldId = :worldId ")
    List<User> findWorldByUserId(@Param("userId") Long userId, @Param("worldId") Long worldId);
}
