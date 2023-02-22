package cmc.repository;

import cmc.domain.World;
import cmc.domain.WorldHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorldHashtagRepository extends JpaRepository<WorldHashtag, Long> {
    @Query(value = "SELECT w " +
            "FROM WorldHashtag wh " +
            "JOIN wh.world w " +
            "JOIN wh.hashtag h " +
            "WHERE w.worldName LIKE CONCAT('%', :keyword, '%') " +
            "OR h.hashtagName LIKE :keyword " +
            "ORDER BY w.createdAt desc ")
    List<World> searchWorld(@Param("keyword") String keyword);
}
