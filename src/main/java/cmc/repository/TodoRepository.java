package cmc.repository;

import cmc.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 세계관 todo 리스트와 오늘 각각 몇명이 했는지 avatar_todo를 join하여 반환
    @Query(value = "SELECT t FROM Todo t " +
            "LEFT JOIN t.avatarTodos at ON t.todoId = at.todo.todoId " +
            "WHERE t.world.worldId = :worldId ")
    List<Todo> getWorldTodoByWorldId(@Param("worldId") Long worldId);

}
