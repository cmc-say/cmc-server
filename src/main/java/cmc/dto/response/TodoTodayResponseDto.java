package cmc.dto.response;

import cmc.domain.AvatarTodo;
import cmc.domain.Todo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TodoTodayResponseDto {
    private Long todoId;
    private String todoContent;
    private List<AvatarTodoResponseDto> avatarTodos;

    @Builder
    TodoTodayResponseDto(Long todoId, String todoContent, List<AvatarTodo> avatarTodos) {
        this.todoId = todoId;
        this.todoContent = todoContent;
        this.avatarTodos = avatarTodos.stream().map(AvatarTodoResponseDto::fromEntity).collect(Collectors.toList());
    }

    public static TodoTodayResponseDto fromEntity(Todo todo) {
        return TodoTodayResponseDto.builder()
                .todoId(todo.getTodoId())
                .todoContent(todo.getTodoContent())
                .avatarTodos(todo.getAvatarTodos())
                .build();
    }
}