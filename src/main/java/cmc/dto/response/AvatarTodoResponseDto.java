package cmc.dto.response;

import cmc.domain.AvatarTodo;
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

    public static AvatarTodoResponseDto fromEntity(AvatarTodo avatarTodo) {
        return AvatarTodoResponseDto.builder()
                .avatarTodoId(avatarTodo.getAvatarTodoId())
                .todoId(avatarTodo.getTodo().getTodoId())
                .avatarId(avatarTodo.getAvatar().getAvatarId())
                .build();
    }
}
