package cmc.repository;

import cmc.domain.CheckedTodo;
import cmc.dto.response.AvatarCheckedTodoResponseDto;
import cmc.dto.response.CountCheckedTodoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CheckedTodoRepository extends JpaRepository<CheckedTodo, Long> {
    // 세계관 todo 당 몇명이 했는지 반환 (count, todoId, todoContent) - checked todo 기준 groupby
    @Query(value = "SELECT t.todoId as todoId, t.todoContent as todoContent, count(ct.checkedTodoId) as count " +
            "FROM Todo t " +
            "LEFT JOIN t.checkedTodos ct ON date(ct.createdAt) = current_date " +
            "WHERE t.world.worldId = :worldId " +
            "GROUP BY t.todoId ")
    List<CountCheckedTodoResponseDto> getCheckedTodoTodayByWorldId(@Param("worldId") Long worldId);

    @Query(value = "SELECT ct FROM CheckedTodo ct " +
            "WHERE ct.todo.todoId = :todoId " +
            "AND ct.avatar.avatarId = :avatarId " +
            "AND date(ct.createdAt) = current_date ")
    Optional<CheckedTodo> findByTodoIdAndAvatarIdToday(@Param("todoId") Long todoId, @Param("avatarId") Long avatarId);

    @Query(value = "SELECT t.todoId as todoId, t.todoContent as todoContent, ct.checkedTodoId as checkedTodoId FROM Todo t " +
            "LEFT JOIN t.checkedTodos ct ON (date(ct.createdAt) = current_date AND ct.avatar.avatarId = :avatarId)" +
            "WHERE t.world.worldId = :worldId ")
    List<AvatarCheckedTodoResponseDto> getCheckedTodoTodayByWorldIdAndAvatarId(@Param("avatarId") Long avatarId, @Param("worldId") Long worldId);

    @Query(value = "SELECT ct FROM CheckedTodo ct " +
            "WHERE ct.avatar.avatarId = :avatarId " +
            "AND month(ct.createdAt) = month(current_date) ")
    List<CheckedTodo> getAllCheckedTodoOfAvatarForMonth(@Param("avatarId") Long avatarId);
}
