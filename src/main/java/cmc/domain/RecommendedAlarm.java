package cmc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "recommended_alarm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecommendedAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendedAlarmId;

    @Column(length = 20)
    private String recommendedAlarmContent;
}
