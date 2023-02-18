package cmc.domain.User.entity;

import cmc.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "Block", indexes = {
        @Index(columnList = "blockedUserId"),
})
@EqualsAndHashCode(of = {"blockId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Block extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockId;

    @Setter @Column(nullable = false, length = 50)
    private Long blockingUserId;

    @Setter @Column(nullable = false, length = 50)
    private Long blockedUserId;

    @Builder
    public Block(Long blockId, Long blockingUserId, Long blockedUserId) {
        this.blockId = blockId;
        this.blockingUserId = blockingUserId;
        this.blockedUserId = blockedUserId;
    }
}
