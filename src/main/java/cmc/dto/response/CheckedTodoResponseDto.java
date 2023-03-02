package cmc.dto.response;

import cmc.domain.CheckedTodo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckedTodoResponseDto {

    @Schema(description = "checkedTodo id")
    private Long checkedTodoId;

    @Schema(description = "todo id")
    private Long todoId;

    @Builder
    CheckedTodoResponseDto(Long checkedTodoId, Long todoId) {
        this.checkedTodoId = checkedTodoId;
        this.todoId = todoId;
    }

    public static CheckedTodoResponseDto fromEntity(CheckedTodo checkedTodo) {
        return CheckedTodoResponseDto.builder()
                .checkedTodoId(checkedTodo.getCheckedTodoId())
                .todoId(checkedTodo.getTodo().getTodoId())
                .build();
    }
}
