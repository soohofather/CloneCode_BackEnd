package org.zerok.mall.service;

import java.util.List;
import java.util.UUID;
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

    @Test
    public void testRegister() {

        ProductDto productDto = ProductDto.builder()
                .pname("새로운 상품")
                .pdesc("신규 추가 상품입니다.")
                .price(1000)
                .build();

        // uuid가 있어야 함
        productDto.setUploadedFileNames(
                List.of(
                        UUID.randomUUID() + "_" + "Test1.jpg",
                        UUID.randomUUID() + "_" + "Test2.jpg"
                )
        );
        productService.register(productDto);
    }
}
