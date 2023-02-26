package cmc.repository;

import cmc.domain.World;
import cmc.domain.WorldHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorldHashtagRepository extends JpaRepository<WorldHashtag, Long> {
}
