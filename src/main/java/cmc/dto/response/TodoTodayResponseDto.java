package cmc.dto.response;

import cmc.domain.CheckedTodo;
import lombok.Builder;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter
public class TodoTodayResponseDto {
    private Long todoId;
    private Long avatarTodoId;

    @Builder
    TodoTodayResponseDto(Long todoId, Long avatarTodoId) {
        this.todoId = todoId;
        this.todoContent = todoContent;
        this.avatarTodos = avatarTodos.stream().map(AvatarTodoResponseDto::fromEntity).collect(Collectors.toList());
    }

    public static TodoTodayResponseDto fromEntity(CheckedTodo checkedTodo) {
        return TodoTodayResponseDto.builder()
                .todoId(checkedTodo.getTodo().getTodoId())
                .todoContent(checkedTodo.getTodo().getTodoContent())
                .avatarTodos(checkedTodo.getAvatarTodos())
                .build();
    }
}