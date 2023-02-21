package cmc.repository;

import cmc.domain.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    @Query(value = "SELECT a.* FROM avatar a LEFT JOIN user u on a.user_id = u.user_id WHERE a.user_id = :userId", nativeQuery = true)
    List<Avatar> findAllByUser(@Param("userId") Long userId);
}
