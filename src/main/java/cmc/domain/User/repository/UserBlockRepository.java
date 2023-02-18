package cmc.domain.User.repository;

import cmc.domain.User.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBlockRepository extends JpaRepository<Block, Long> {
}
