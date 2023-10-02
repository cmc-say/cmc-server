package cmc.domain;

import cmc.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "checked_todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Entity
public class CheckedTodo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkedTodoId;

    @ManyToOne
    @JoinColumn(name = "todoId")
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "avatarId")
    private Avatar avatar;

    @Builder
    public CheckedTodo(
            Long checkedTodoId,
            Todo todo,
            Avatar avatar) {

        this.checkedTodoId = checkedTodoId;
        this.todo = todo;
        this.avatar = avatar;
    }

}
