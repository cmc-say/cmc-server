package cmc.repository;

import cmc.domain.CheckedTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckedTodoRepository extends JpaRepository<CheckedTodo, Long> {
    @Query(value = "SELECT at FROM CheckedTodo at " +
            "JOIN at.todo t " +
            "WHERE t.world.worldId = :worldId " +
            "AND date(at.createdAt) = current_date ")
    List<CheckedTodo> getAvatarTodoTodayByWorldId(@Param("worldId") Long worldId);
}
