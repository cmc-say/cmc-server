package cmc.repository;

import cmc.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findBlockByBlockingUserIdAndBlockedUserId(Long blockingUserId, Long blockedUserId);
}
