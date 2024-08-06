package org.zerok.mall.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.dto.PageResponseDto;
import org.zerok.mall.dto.ProductDto;

@SpringBootTest
@Log4j2
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @Test
    public void testList() {

        PageRequestDto pageRequestDto = PageRequestDto.builder().build();

        PageResponseDto<ProductDto> responseDto = productService.getList(pageRequestDto);

        log.info(responseDto.getDtoList());
    }

}
