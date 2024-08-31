package org.zerok.mall.repository.search;

import org.springframework.data.domain.Page;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.entity.TodoEntity;

public interface TodoSearch {

    Page<TodoEntity> search1(PageRequestDto pageRequestDto);

}
