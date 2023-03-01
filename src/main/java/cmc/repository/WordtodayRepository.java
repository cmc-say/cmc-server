package cmc.repository;

import cmc.domain.Wordtoday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordtodayRepository extends JpaRepository<Wordtoday, Long> {
}
