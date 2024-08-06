package org.zerok.mall.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.dto.PageResponseDto;
import org.zerok.mall.dto.ProductDto;

@Transactional
public interface ProductService {

    PageResponseDto<ProductDto> getList(PageRequestDto pageRequestDto);

}
