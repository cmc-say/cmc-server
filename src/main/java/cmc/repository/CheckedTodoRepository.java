package cmc.repository;

import cmc.domain.CheckedTodo;
import cmc.dto.response.CountCheckedTodoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckedTodoRepository extends JpaRepository<CheckedTodo, Long> {
    @Query(value = "select count(ct.world_avatar_id) as count, t.todo_id as todoId, t.todo_content as todoContent from checked_todo ct\n" +
            "left join todo t on ct.todo_id = t.todo_id\n" +
            "where t.world_id = :worldId and date(ct.created_at) = current_date\n" +
            "group by ct.todo_id;", nativeQuery = true)
    List<CountCheckedTodoResponse> getCheckedTodoTodayByWorldId(@Param("worldId") Long worldId);

}
