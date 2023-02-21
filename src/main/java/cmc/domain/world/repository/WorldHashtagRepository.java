package cmc.domain.world.repository;

import cmc.domain.world.dto.response.GetWorldsByAvatarResponse;
import cmc.domain.world.entity.World;
import cmc.domain.world.entity.WorldHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorldHashtagRepository extends JpaRepository<WorldHashtag, Long> {
}
