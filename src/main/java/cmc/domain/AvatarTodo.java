package cmc.domain;

import cmc.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "avatar_todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AvatarTodo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatarTodoId;

    @ManyToOne
    @JoinColumn(name = "todoId")
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "avatarId")
    private Avatar avatar;

    @Builder
    public AvatarTodo(
            Long avatarTodoId,
            Todo todo,
            Avatar avatar) {

        this.avatarTodoId = avatarTodoId;
        this.todo = todo;
        this.avatar = avatar;
    }

}
