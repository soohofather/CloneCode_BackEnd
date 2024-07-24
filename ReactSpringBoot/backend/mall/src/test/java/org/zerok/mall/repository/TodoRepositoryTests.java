package org.zerok.mall.repository;

import java.time.LocalDate;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerok.mall.entity.TodoEntity;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test1() {

        Assertions.assertNotNull(todoRepository);

        log.info(todoRepository.getClass().getName());
    }

    @Test
    public void testInsert() {

        TodoEntity todo = TodoEntity.builder()
                .title("title")
                .content("content...")
                .dueDate(LocalDate.of(2023,12,30))
                .build();

        TodoEntity result = todoRepository.save(todo);

        log.info(result);
    }

    @Test
    public void testRead() {

        Long tno = 1L;

        Optional<TodoEntity> result = todoRepository.findById(tno);

        TodoEntity todoEntity = result.orElseThrow();

        log.info(todoEntity);
    }

    @Test
    public void testUpdate() {

        // 먼저 로딩 하고,
        Long tno = 1L;

        Optional<TodoEntity> result = todoRepository.findById(tno);

        TodoEntity todoEntity = result.orElseThrow();

        // 엔티티 변경
        todoEntity.setTitle("Updated Title");
        todoEntity.setContent("Updated Content");
        todoEntity.setComplete(true);

        todoRepository.save(todoEntity);
    }

}
