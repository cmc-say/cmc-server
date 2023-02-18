package cmc.domain.User.repository;

import cmc.domain.User.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<Report, Long> {
}
