package cmc.repository;

import cmc.domain.Avatar;
import cmc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT a FROM User u LEFT JOIN Avatar a on a.user.userId = u.userId WHERE u.userId = :userId")
    List<Avatar> findAvatarsByUser(@Param("userId") Long userId);
}
