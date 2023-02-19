package cmc.domain.user.entity;

import cmc.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "Report", indexes = {
        @Index(columnList = "reportedUserId"),
})
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

    @Builder
    public Report(Long reportId, Long reportingUserId, Long reportedUserId) {
        this.reportId = reportId;
        this.reportingUserId = reportingUserId;
        this.reportedUserId = reportedUserId;
    }
}
