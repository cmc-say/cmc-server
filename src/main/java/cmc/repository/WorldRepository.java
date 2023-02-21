package cmc.repository;

import cmc.domain.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorldRepository extends JpaRepository<World, Long> {

    @Query(value = "SELECT w " +
            "FROM World w " +
            "ORDER BY w.createdAt desc ")
    List<World> getWorldsWithOrderRecent();
}
