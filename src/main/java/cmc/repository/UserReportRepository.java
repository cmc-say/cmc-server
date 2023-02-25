package cmc.repository;

import cmc.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserReportRepository extends JpaRepository<Report, Long> {
    @Query(value = "SELECT r " +
            "FROM Report r " +
            "WHERE r.reportedUser.userId = :reportingUserId " +
            "AND r.reportingUserId = :reportedUserId ")
    List<Report> findByReportingUserIdAndReportedUser(@Param("reportingUserId") Long reportingUserId, @Param("reportedUserId") Long reportedUserId);

    @Query(value = "SELECT r " +
            "FROM Report r " +
            "JOIN r.reportedUser ru " +
            "WHERE ru.userId = :reportedUserId ")
    List<Report> findReportByReportedUserId(@Param("reportedUserId") Long reportedUserId);
}
