package cmc.repository;

import cmc.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBlockRepository extends JpaRepository<Block, Long> {
}
