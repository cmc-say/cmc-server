package cmc.repository;

import cmc.domain.CheckedTodo;
import cmc.dto.response.CountCheckedTodoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CheckedTodoRepository extends JpaRepository<CheckedTodo, Long> {
    // 세계관 todo 당 몇명이 했는지 반환 (count, todoId, todoContent) - checked todo 기준 groupby
    @Query(value = "SELECT ct.todo.todoId as todoId, ct.todo.todoContent as todoContent, count(ct.checkedTodoId) as count FROM CheckedTodo ct " +
            "JOIN ct.worldAvatar wa " +
            "WHERE wa.world.worldId = :worldId " +
            "AND date(ct.createdAt) = current_date " +
            "GROUP BY ct.todo.todoId ")
    List<CountCheckedTodoResponse> getCheckedTodoTodayByWorldId(@Param("worldId") Long worldId);

    @Query(value = "SELECT ct FROM CheckedTodo ct " +
            "WHERE ct.todo.todoId = :todoId " +
            "AND ct.worldAvatar.worldAvatarId = :worldAvatarId " +
            "AND date(ct.createdAt) = current_date ")
    Optional<CheckedTodo> findByTodoIdAndWorldAvatarIdToday(@Param("todoId") Long todoId, @Param("worldAvatarId") Long worldAvatarId);

    @Query(value = "SELECT ct FROM CheckedTodo ct " +
            "WHERE ct.todo.world.worldId = :worldId " +
            "AND ct.worldAvatar.avatar.avatarId = :avatarId ")
    List<CheckedTodo> getCheckedTodoTodayByWorldIdAndAvatarId(@Param("avatarId") Long avatarId, @Param("worldId") Long worldId);

    @Query(value = "SELECT ct FROM CheckedTodo ct " +
            "WHERE ct.worldAvatar.avatar.avatarId = :avatarId " +
            "AND month(ct.createdAt) = month(current_date) ")
    List<CheckedTodo> getAllCheckedTodoOfAvatarForMonth(@Param("avatarId") Long avatarId);
}
