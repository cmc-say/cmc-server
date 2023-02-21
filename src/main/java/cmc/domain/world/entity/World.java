package cmc.domain.world.entity;

import cmc.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "World")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class World extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long worldId;

    @Setter
    @Column(nullable = false, length = 14)
    private String worldName;

    @Setter
    @Column(nullable = false)
    private Integer worldUserLimit;

    @Setter
    @Column(length = 200)
    private String worldImg;

    @Setter
    @Column(nullable = false)
    private LocalDateTime worldStartDate;

    @Setter
    @Column(nullable = false)
    private LocalDateTime worldEndDate;

    @Setter
    @Column(length = 200)
    private String worldNotice;

    @Setter
    @Column(length = 20)
    private String worldPassword;

    @Setter
    private Long worldHostUserId;

    @OneToMany(targetEntity = WorldHashtag.class, fetch = FetchType.LAZY, mappedBy = "world")
    private List<WorldHashtag> worldHashtags = new ArrayList<>();

    @Builder
    public World(
            Long worldId,
            String worldName,
            Integer worldUserLimit,
            String worldImg,
            LocalDateTime worldStartDate,
            LocalDateTime worldEndDate,
            String worldNotice,
            String worldPassword,
            Long worldHostUserId) {

        this.worldId = worldId;
        this.worldName = worldName;
        this.worldUserLimit = worldUserLimit;
        this.worldImg = worldImg;
        this.worldStartDate = worldStartDate;
        this.worldEndDate = worldEndDate;
        this.worldNotice = worldNotice;
        this.worldPassword = worldPassword;
        this.worldHostUserId = worldHostUserId;
    }
}
