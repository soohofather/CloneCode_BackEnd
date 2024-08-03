package org.zerok.mall.service;

import java.time.LocalDate;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.dto.TodoDto;

@SpringBootTest
@Log4j2
public class TodoServiceTests {

    @Autowired
    TodoService  todoService;

    @Test
    public void testGet() {

        Long tno = 50L;

        log.info(todoService.get(tno));
    }

    @Test
    public void testRegister() {

        TodoDto todoDto = TodoDto.builder()
                .title("TITLE....")
                .content("CONTENT......")
                .dueDate(LocalDate.of(2023,12,31))
                .build();

        log.info(todoService.register(todoDto));
    }

    @Test
    public void testGetList() {

        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(4)
                .build();

        log.info(todoService.getList(pageRequestDto));
    }
}
