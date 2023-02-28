package cmc.dto.response;

import cmc.domain.CheckedTodo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarTodoResponseDto {
    private Long avatarTodoId;
    private Long todoId;
    private Long avatarId;

    @Builder
    AvatarTodoResponseDto(Long avatarTodoId, Long todoId, Long avatarId) {
        this.avatarTodoId = avatarTodoId;
        this.todoId = todoId;
        this.avatarId = avatarId;
    }

    public static AvatarTodoResponseDto fromEntity(CheckedTodo checkedTodo) {
        return AvatarTodoResponseDto.builder()
                .avatarTodoId(checkedTodo.getAvatarTodoId())
                .todoId(checkedTodo.getTodo().getTodoId())
                .avatarId(checkedTodo.getAvatar().getAvatarId())
                .build();
    }
}
