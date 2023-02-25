package cmc.domain;

import cmc.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "block",
        indexes = {
            @Index(columnList = "blockingUserId"),
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name= "blocking_blocked_constraint",
                        columnNames={"blockingUserId", "blockedUserId"}
                )
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Block extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockId;

    @Setter @Column(nullable = false, length = 50)
    private Long blockingUserId;

    @ManyToOne
    @JoinColumn(name = "blockedUserId")
    private User blockedUser;

    @Builder
    public Block(Long blockId, Long blockingUserId, User blockedUser) {
        this.blockId = blockId;
        this.blockingUserId = blockingUserId;
        this.blockedUser = blockedUser;
    }
}
