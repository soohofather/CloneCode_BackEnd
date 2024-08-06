package org.zerok.mall.service.Impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.dto.PageResponseDto;
import org.zerok.mall.dto.ProductDto;
import org.zerok.mall.entity.ProductEntity;
import org.zerok.mall.entity.ProductImage;
import org.zerok.mall.repository.ProductRepository;
import org.zerok.mall.service.ProductService;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public PageResponseDto<ProductDto> getList(PageRequestDto pageRequestDto) {

        Pageable pageable = PageRequest.of(
                pageRequestDto.getPage()-1,
                pageRequestDto.getSize(),
                Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        // Object[] => 0 product, 1 productImage
        List<ProductDto> dtoList = result.get().map(arr -> {

            ProductDto productDto = null;

            ProductEntity productEntity = (ProductEntity) arr[0];
            ProductImage productImage = (ProductImage) arr[1];

            productDto = ProductDto.builder()
                    .pno(productEntity.getPno())
                    .pname(productEntity.getPname())
                    .pdesc(productEntity.getPdesc())
                    .price(productEntity.getPrice())
                    .build();

            String imageStr = productImage.getFileName();
            productDto.setUploadedFileNames(List.of(imageStr));

            return productDto;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDto.<ProductDto>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDto(pageRequestDto)
                .build();
    }

}
