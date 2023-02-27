package cmc.repository;

import cmc.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    @Query(value = "SELECT h " +
            "FROM Hashtag h " +
            "WHERE h.hashtagName IN :hashtagNames ")
    List<Hashtag> findHashtagByNameIn(@Param("hashtagNames") List<String> hashtagNames);

    @Query(value = "SELECT h " +
            "FROM WorldHashtag wh " +
            "JOIN wh.hashtag h " +
            "GROUP BY h.hashtagId " +
            "ORDER BY COUNT(wh.worldHashtagId) DESC")
    List<Hashtag> getHashtagWithOrderPopular();
}
