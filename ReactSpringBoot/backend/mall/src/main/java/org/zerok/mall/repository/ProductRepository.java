package org.zerok.mall.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerok.mall.entity.ProductEntity;
import org.zerok.mall.repository.search.ProductSearch;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductSearch {

    @EntityGraph(attributePaths = "imageList")
    @Query("select p from ProductEntity p where p.pno = :pno")
    Optional<ProductEntity> selectOne(@Param("pno")Long pno);

    @Modifying
    @Query("update ProductEntity p set p.delFlag = :delFlag where p.pno = :pno")
    void updateToDelete(@Param("pno")Long pno, @Param("delFlag") boolean flag);

    @Query("select p,pi from ProductEntity p left join p.imageList pi where pi.ord = 0 and p.delFlag = false ")
    Page<Object[]> selectList(Pageable pageable);
}
