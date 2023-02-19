package cmc.domain.user.repository;

import cmc.domain.user.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<Report, Long> {
}
