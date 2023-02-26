package cmc.repository;

import cmc.domain.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorldRepository extends JpaRepository<World, Long> {
    @Query(value = "SELECT w " +
            "FROM WorldAvatar wa " +
            "JOIN wa.world w " +
            "JOIN w.worldHashtags wh " +
            "JOIN wh.hashtag " +
            "WHERE wa.avatar.avatarId = :avatarId")
    List<World> findWorldWithAvatar(@Param("avatarId") Long avatarId);

    @Query(value = "SELECT w " +
            "FROM World w " +
            "ORDER BY w.createdAt desc ")
    List<World> getWorldsWithOrderRecent();

    @Query(value = "SELECT w " +
            "FROM WorldHashtag wh " +
            "JOIN wh.world w " +
            "JOIN wh.hashtag h " +
            "WHERE w.worldName LIKE CONCAT('%', :keyword, '%') " +
            "OR h.hashtagName LIKE :keyword " +
            "ORDER BY w.createdAt desc ")
    List<World> searchWorld(@Param("keyword") String keyword);
}
