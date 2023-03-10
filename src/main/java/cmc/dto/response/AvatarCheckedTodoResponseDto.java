package cmc.dto.response;

import cmc.domain.CheckedTodo;

import java.util.Optional;

public interface AvatarCheckedTodoResponseDto {
    Optional<Long> getCheckedTodoId();
    Long getTodoId();
    String getTodoContent();
}
