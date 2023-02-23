package cmc.domain;

import cmc.common.BaseEntity;
import cmc.domain.model.ReportType;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(
        name = "report",
        indexes = {
        @Index(columnList = "reportingUserId"),
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "reporting_reported_constraint",
                        columnNames = {"reportingUserId", "reportedUserId"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Setter @Column(nullable = false, length = 50)
    private Long reportingUserId;

    @Setter @Column(nullable = false, length = 50)
    private Long reportedUserId;

    @Enumerated(EnumType.STRING)
    @Setter @Column(nullable = false, length = 20)
    private ReportType reportType;

    @Builder
    public Report(Long reportId, Long reportingUserId, Long reportedUserId, ReportType reportType) {
        this.reportId = reportId;
        this.reportingUserId = reportingUserId;
        this.reportedUserId = reportedUserId;
        this.reportType = reportType;
    }
}
