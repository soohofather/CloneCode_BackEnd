package org.zerok.mall.repository.search;

import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.dto.PageResponseDto;
import org.zerok.mall.dto.ProductDto;

public interface ProductSearch {

    PageResponseDto<ProductDto> searchList (PageRequestDto pageRequestDto);



}
