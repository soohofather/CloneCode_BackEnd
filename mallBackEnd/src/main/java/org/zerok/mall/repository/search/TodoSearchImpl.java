package org.zerok.mall.repository.search;

import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.entity.QTodoEntity;
import org.zerok.mall.entity.TodoEntity;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl() {
        super(TodoEntity.class);
    }

    @Override
    public Page<TodoEntity> search1(PageRequestDto pageRequestDto) {

        log.info("search1...............................");

        QTodoEntity todoEntity = QTodoEntity.todoEntity;

        JPQLQuery<TodoEntity> query = from(todoEntity);

        query.where(todoEntity.title.contains("1"));

        Pageable pageable = PageRequest.of(
                pageRequestDto.getPage() - 1,
                pageRequestDto.getSize(),
                Sort.by("tno").descending()
        );

        this.getQuerydsl().applyPagination(pageable, query);

        List<TodoEntity> list = query.fetch(); // 목록 데이터 가져올때

        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }

}
