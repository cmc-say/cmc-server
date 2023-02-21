package cmc.domain.world.repository;

import cmc.domain.world.entity.RecommendedWorld;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedWorldRepository extends JpaRepository<RecommendedWorld, Long> {
}
