package cmc.domain.user.repository;

import cmc.domain.user.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBlockRepository extends JpaRepository<Block, Long> {
}
