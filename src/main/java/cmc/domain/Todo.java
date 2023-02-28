package cmc.domain;

import cmc.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @ManyToOne
    @JoinColumn(name = "worldId")
    private World world;

    @Setter
    private String todoContent;

    @Builder
    public Todo(
            Long todoId,
            World world,
            String todoContent) {

        this.todoId = todoId;
        this.world = world;
        this.todoContent = todoContent;
    }
}
