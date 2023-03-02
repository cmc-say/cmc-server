package cmc.domain;

import cmc.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Column(length = 50)
    private String todoContent;

    @OneToMany(targetEntity = CheckedTodo.class, fetch = FetchType.LAZY, mappedBy = "todo", cascade = CascadeType.ALL)
    private List<CheckedTodo> checkedTodos = new ArrayList<>();

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
