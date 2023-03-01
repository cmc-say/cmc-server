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
    @Query(value = "select t.todo_id as todoId, t.todo_content as todoContent, count(ct.checked_todo_id) as count from todo t\n" +
            "left join checked_todo ct on t.todo_id = ct.todo_id\n" +
            "where t.world_id = :worldId\n" +
            "and (date(ct.created_at) = current_date or ct.created_at is null)\n" +
            "group by t.todo_id;", nativeQuery = true)
    List<CountCheckedTodoResponse> getCheckedTodoTodayByWorldId(@Param("worldId") Long worldId);

    @Query(value = "SELECT ct FROM CheckedTodo ct " +
            "WHERE ct.todo.todoId = :todoId " +
            "AND ct.worldAvatar.worldAvatarId = :worldAvatarId ")
    Optional<CheckedTodo> findByTodoIdAndWorldAvatarId(@Param("todoId") Long todoId, @Param("worldAvatarId") Long worldAvatarId);
}
