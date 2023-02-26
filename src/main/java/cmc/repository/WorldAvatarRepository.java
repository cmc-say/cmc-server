package cmc.repository;

import cmc.domain.User;
import cmc.domain.WorldAvatar;
import cmc.domain.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorldAvatarRepository extends JpaRepository<WorldAvatar, Long> {
}
