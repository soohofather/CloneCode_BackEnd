package org.zerok.mall.repository;

import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerok.mall.entity.ProductEntity;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {

        ProductEntity productEntity = ProductEntity.builder()
                .pname("Test")
                .pdesc("Test Desc")
                .price(1000)
                .build();
        productEntity.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jpg");

        productEntity.addImageString(UUID.randomUUID()+"_"+"IMAGE2.jpg");

        productRepository.save(productEntity);
    }

}
