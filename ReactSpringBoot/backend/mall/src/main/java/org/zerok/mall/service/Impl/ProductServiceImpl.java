package org.zerok.mall.service.Impl;

import java.util.List;
import java.util.Optional;
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

            ProductEntity productEntity = (ProductEntity) arr[0];
            ProductImage productImage = (ProductImage) arr[1];

            ProductDto productDto = ProductDto.builder()
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

    public Long register(ProductDto productDto) {

        ProductEntity productEntity = dtoToEntity(productDto);

        log.info("---------------------------------------");
        log.info(productEntity);
        log.info(productEntity.getImageList());

        Long pno = productRepository.save(productEntity).getPno();

        return pno;
    }

    @Override
    public ProductDto get(Long pno) {

        Optional<ProductEntity> result = productRepository.findById(pno);

        ProductEntity productEntity = result.orElseThrow();

        return entityToDto(productEntity);
    }

    @Override
    public void modify(ProductDto productDto) {
        // 조회
        Optional<ProductEntity> result = productRepository.findById(productDto.getPno());

        ProductEntity productEntity = result.orElseThrow();

        // 변경 내용 반영
        productEntity.setPrice(productDto.getPrice());
        productEntity.setPname(productDto.getPname());
        productEntity.setPdesc(productDto.getPname());
        productEntity.setDelFlag(productDto.isDelFlag());

        // 이미지 처리
        List<String> uploadFileNames = productDto.getUploadedFileNames();

        productEntity.clearList();

        if (uploadFileNames != null && !uploadFileNames.isEmpty()) {

            uploadFileNames.forEach(uploadName -> {
                productEntity.addImageString(uploadName);
            });
        }

        // 저장
        productRepository.save(productEntity);
    }

    private ProductDto entityToDto(ProductEntity productEntity) {

        ProductDto productDto = ProductDto.builder()
                .pno(productEntity.getPno())
                .pname(productEntity.getPname())
                .pdesc(productEntity.getPdesc())
                .price(productEntity.getPrice())
                .delFlag(productEntity.isDelFlag())
                .build();

        List<ProductImage> imageList = productEntity.getImageList();

        if(imageList == null || imageList.isEmpty()) {
            return productDto;
        }

        List<String> fileNameList = imageList.stream().map(productImage ->
                productImage.getFileName()).toList();

        productDto.setUploadedFileNames(fileNameList);

        return productDto;
    }

    private ProductEntity dtoToEntity(ProductDto productDto) {

        ProductEntity productEntity = ProductEntity.builder()
                .pno(productDto.getPno())
                .pname(productDto.getPname())
                .pdesc(productDto.getPdesc())
                .price(productDto.getPrice())
                .delFlag(productDto.isDelFlag())
                .build();

        List<String> uploadFileNames = productDto.getUploadedFileNames();

        if(uploadFileNames == null || uploadFileNames.size() == 0) {
            return productEntity;
        }

        uploadFileNames.forEach(fileName -> {
            productEntity.addImageString(fileName);
        });

        return productEntity;
    }
}
