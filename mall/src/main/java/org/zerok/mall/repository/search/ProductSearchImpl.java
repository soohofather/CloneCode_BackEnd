package org.zerok.mall.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.dto.PageResponseDto;
import org.zerok.mall.dto.ProductDto;
import org.zerok.mall.entity.ProductEntity;
import org.zerok.mall.entity.QProductEntity;
import org.zerok.mall.entity.QProductImage;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(ProductEntity.class);
    }

    @Override
    public PageResponseDto<ProductDto> searchList(PageRequestDto pageRequestDto) {

        log.info("--------------------------------searchList----------------------------");

        Pageable pageable = PageRequest.of(
                pageRequestDto.getPage() -1,
                pageRequestDto.getSize(),
                Sort.by("pno").descending());

        QProductEntity productEntity = QProductEntity.productEntity;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<ProductEntity> query = from(productEntity);
        query.leftJoin(productEntity.imageList, productImage);

        query.where(productImage.ord.eq(0));

        Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);

        List<Tuple> productEntityList = query.select(productEntity, productImage).fetch();

        long count = query.fetchCount();

        log.info("===============================================");
        log.info(productEntityList);

        return null;
    }

}
