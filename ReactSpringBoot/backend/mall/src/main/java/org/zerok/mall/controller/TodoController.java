package org.zerok.mall.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.dto.PageResponseDto;
import org.zerok.mall.dto.TodoDto;
import org.zerok.mall.service.TodoService;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{tno}")
    public TodoDto get(@PathVariable("tno") Long tno) {

        return todoService.get(tno);
    }

    @GetMapping("/list")
    public PageResponseDto<TodoDto> list(PageRequestDto pageRequestDto) {

        log.info("list............................" + pageRequestDto);

        return todoService.getList(pageRequestDto);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDto dto) {

        log.info("todoDto: " + dto);

        long tno = todoService.register(dto);

        return Map.of("TNO", tno);
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(
            @PathVariable("tno") Long tno,
            @RequestBody TodoDto todoDto
    ) {
        todoDto.setTno(tno);

        todoService.modify(todoDto);

        return Map.of("RESULT","SUCCESS");
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable Long tno) {

        todoService.remove(tno);

        return Map.of("RESULT","SUCCESS");
    }

}
