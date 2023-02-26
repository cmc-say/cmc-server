package cmc.repository;

import cmc.domain.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    @Query(value = "SELECT a FROM Avatar a LEFT JOIN User u on a.user.userId = u.userId WHERE u.userId = :userId")
    List<Avatar> findAvatarsByUser(@Param("userId") Long userId);
}
