package cmc.repository;

import cmc.domain.RecommendedAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedAlarmRepository extends JpaRepository<RecommendedAlarm, Long> {
}
