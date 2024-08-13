package org.zerok.mall.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.dto.PageResponseDto;
import org.zerok.mall.dto.TodoDto;
import org.zerok.mall.entity.TodoEntity;

@Transactional
public interface TodoService {

    TodoDto get(Long tno);

    Long register(TodoDto dto);

    void modify(TodoDto dto);

    void remove (Long tno);

    PageResponseDto<TodoDto> getList(PageRequestDto pageRequestDto);

    default TodoDto entityToDto(TodoEntity entity) {

        return TodoDto.builder()
                .tno(entity.getTno())
                .title(entity.getTitle())
                .complete(entity.isComplete())
                .dueDate(entity.getDueDate())
                .build();

    }

    default TodoEntity DtoToEntity(TodoDto todoDto) {

        return TodoEntity.builder()
                .tno(todoDto.getTno())
                .title(todoDto.getTitle())
                .complete(todoDto.isComplete())
                .dueDate(todoDto.getDueDate())
                .build();
    }
}
