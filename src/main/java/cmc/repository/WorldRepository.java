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
            "WHERE w.worldEndDate >= CURRENT_DATE ")
    List<World> getWorldsWithoutExpiredWorld();

    @Query(value = "SELECT w " +
            "FROM World w " +
            "WHERE w.worldEndDate >= CURRENT_DATE " +
            "ORDER BY w.createdAt desc ")
    List<World> getWorldsWithOrderRecentWithoutExpiredWorld();

    @Query(value = "SELECT distinct w " +
            "FROM WorldHashtag wh " +
            "JOIN wh.world w " +
            "JOIN wh.hashtag h " +
            "WHERE (w.worldName LIKE CONCAT('%', :keyword, '%') " +
            "OR h.hashtagName LIKE :keyword) " +
            "AND w.worldEndDate >= CURRENT_DATE " +
            "ORDER BY w.createdAt desc ")
    List<World> searchWorldByWorldNameAndHashtagNameWithOrderRecentWithoutExpiredWorld(@Param("keyword") String keyword);

    @Query(value = "SELECT distinct w " +
            "FROM WorldHashtag wh " +
            "JOIN wh.world w " +
            "JOIN wh.hashtag h " +
            "WHERE (w.worldName LIKE CONCAT('%', :keyword, '%') " +
            "OR h.hashtagName LIKE :keyword) " +
            "AND w.worldEndDate >= CURRENT_DATE ")
    List<World> searchWorldByWorldNameAndHashtagNameWithoutExpiredWorld(@Param("keyword") String keyword);

}
