package org.zerok.mall.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.dto.PageResponseDto;
import org.zerok.mall.dto.TodoDto;
import org.zerok.mall.entity.TodoEntity;
import org.zerok.mall.repository.TodoRepository;
import org.zerok.mall.service.TodoService;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    //자동주입 대상은 final로
    private final ModelMapper modelMapper;

    @Override
    public TodoDto get(Long tno) {

        Optional<TodoEntity> result = todoRepository.findById(tno);

        TodoEntity todoEntity = result.orElseThrow();

        TodoDto todoDto = modelMapper.map(todoEntity, TodoDto.class);

        return todoDto;
    }

    @Override
    public Long register(TodoDto dto) {

        TodoEntity todoEntity = modelMapper.map(dto, TodoEntity.class);

        TodoEntity result = todoRepository.save(todoEntity);

        return result.getTno();
    }

    @Override
    public void modify(TodoDto dto) {

        Optional<TodoEntity> result = todoRepository.findById(dto.getTno());

        TodoEntity todoEntity = result.orElseThrow();

        todoEntity.changeTitle(dto.getTitle());
        todoEntity.changeContent(dto.getContent());
        todoEntity.changeComplete(dto.isComplete());
        todoEntity.changeDueDate(dto.getDueDate());

        todoRepository.save(todoEntity);
    }

    @Override
    public void remove(Long tno) {

        todoRepository.deleteById(tno);
    }

    @Override
    public PageResponseDto<TodoDto> getList(PageRequestDto pageRequestDto) {

        Pageable pageable =
                PageRequest.of(
                        pageRequestDto.getPage() - 1 ,  // 1페이지가 0이므로 주의
                        pageRequestDto.getSize(),
                        Sort.by("tno").descending());

        // JPA
        Page<TodoEntity> result = todoRepository.findAll(pageable);

        List<TodoDto> dtoList = result.getContent().stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        PageResponseDto<TodoDto> responseDto =
                PageResponseDto.<TodoDto>withAll()
                        .dtoList(dtoList)
                        .pageRequestDto(pageRequestDto)
                        .totalCount(totalCount)
                        .build();

        return responseDto;
    }


}
