package cmc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "recommended_world")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecommendedWorld {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendedWorldId;

    @Column(length = 30)
    private String recommendedWorldName;
}
