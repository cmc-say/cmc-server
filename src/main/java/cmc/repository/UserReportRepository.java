package cmc.repository;

import cmc.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findReportByReportingUserIdAndReportedUserId(Long reportingUserId, Long reportedUserId);
}
