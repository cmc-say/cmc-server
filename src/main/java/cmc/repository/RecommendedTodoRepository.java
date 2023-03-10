package cmc.repository;

import cmc.domain.RecommendedTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendedTodoRepository extends JpaRepository<RecommendedTodo, Long> {
    @Query(value = "SELECT rt FROM RecommendedTodo rt " +
            "JOIN rt.recommendedWorld rw " +
            "WHERE rw.recommendedWorldId = :recommendedWorldId")
    List<RecommendedTodo> findAllByRecommendedWorldId(@Param("recommendedWorldId") Long recommendedWorldId);
}
