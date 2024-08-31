package org.zerok.mall.repository;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.entity.ProductEntity;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {

        for(int i = 0; i < 100; i++) {

            ProductEntity productEntity = ProductEntity.builder()
                    .pname(i + "번째 Test")
                    .pdesc(i + "번째 Test Desc")
                    .price(i * 1000)
                    .build();
            productEntity.addImageString(UUID.randomUUID() + "_" + "IMAGE1.jpg");

            productEntity.addImageString(UUID.randomUUID() + "_" + "IMAGE2.jpg");

            productRepository.save(productEntity);
        }
    }

    @Transactional
    @Test
    public void testRead() {

        Long pno = 1L;

        Optional<ProductEntity> result = productRepository.findById(pno);

        ProductEntity productEntity = result.orElseThrow();

        log.info(productEntity);

        log.info(productEntity.getImageList());

    }

    @Test
    public void testRead2() {

        Long pno = 1L;

        Optional<ProductEntity> result = productRepository.selectOne(pno);

        ProductEntity productEntity = result.orElseThrow();

        log.info(productEntity);

        log.info(productEntity.getImageList());
    }

    @Commit
    @Transactional
    @Test
    public void testDelete() {

        Long pno = 2L;

        productRepository.updateToDelete(2L, false);
    }

    @Test
    public void testUpdate() {

        ProductEntity productEntity = productRepository.selectOne(1L).get();

        productEntity.setPrice(3000);

        productEntity.clearList();

        productEntity.addImageString(UUID.randomUUID()+"_"+"PIMAGE1.jpg");
        productEntity.addImageString(UUID.randomUUID()+"_"+"PIMAGE2.jpg");
        productEntity.addImageString(UUID.randomUUID()+"_"+"PIMAGE3.jpg");

        productRepository.save(productEntity);
    }

    @Test
    public void testList() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @Test
    public void testSearch() {

        PageRequestDto pageRequestDto = PageRequestDto.builder().build();

        productRepository.searchList(pageRequestDto);

    }

}
