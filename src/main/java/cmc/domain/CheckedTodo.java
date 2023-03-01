package cmc.domain;

import cmc.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "checked_todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CheckedTodo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkedTodoId;

    @ManyToOne
    @JoinColumn(name = "todoId")
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "worldAvatarId")
    private WorldAvatar worldAvatar;

    @Builder
    public CheckedTodo(
            Long checkedTodoId,
            Todo todo,
            WorldAvatar worldAvatar) {

        this.checkedTodoId = checkedTodoId;
        this.todo = todo;
        this.worldAvatar = worldAvatar;
    }

}
