package cmc.repository;

import cmc.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<Report, Long> {
}
