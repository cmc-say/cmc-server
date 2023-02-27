package cmc.repository;

import cmc.domain.World;
import cmc.domain.WorldHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorldHashtagRepository extends JpaRepository<WorldHashtag, Long> {
    @Modifying
    @Query(value = "DELETE FROM WorldHashtag wh " +
            "WHERE wh.worldHashtagId IN :worldHashtags")
    void deleteWorldHashtagByWorldHashtagId(@Param("worldHashtags") List<String> worldHashtags);
}
