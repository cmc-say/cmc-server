package cmc.repository;

import cmc.domain.Wordtoday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WordtodayRepository extends JpaRepository<Wordtoday, Long> {
    @Query(value = "SELECT wt FROM Wordtoday wt " +
            "JOIN wt.worldAvatar wa " +
            "WHERE wa.world.worldId = :worldId " +
            "AND wa.avatar.avatarId = :avatarId " +
            "AND date(wt.createdAt) = current_date ")
    Optional<Wordtoday> findWordtodayByAvatarIdAndWorldId(@Param("avatarId") Long avatarId, @Param("worldId") Long worldId);
}
