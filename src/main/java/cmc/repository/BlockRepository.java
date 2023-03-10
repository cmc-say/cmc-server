package cmc.repository;

import cmc.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    @Query(value = "SELECT b " +
            "FROM Block b " +
            "JOIN b.blockedUser bu " +
            "WHERE b.blockingUserId = :blockingUserId " +
            "AND bu.userId = :blockedUserId ")
    Optional<Block> findBlockByBlockingUserIdAndBlockedUser(@Param("blockingUserId") Long blockingUserId, @Param("blockedUserId") Long blockedUserId);
}
