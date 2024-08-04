package org.zerok.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerok.mall.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
