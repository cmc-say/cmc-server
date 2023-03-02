package cmc.dto.response;

import cmc.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TodoResponseDto {
    @Schema(description = "todo id")
    private Long todoId;

    @Schema(description = "todo 내용")
    private String todoContent;

    @Builder
    TodoResponseDto(Long todoId, String todoContent) {
        this.todoId = todoId;
        this.todoContent = todoContent;
    }

    public static TodoResponseDto fromEntity(Todo todo) {
        return TodoResponseDto.builder()
                .todoId(todo.getTodoId())
                .todoContent(todo.getTodoContent())
                .build();
    }
}
