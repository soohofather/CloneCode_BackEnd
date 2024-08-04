package org.zerok.mall.entity;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.internal.bytebuddy.dynamic.loading.InjectionClassLoader.Strategy;

@Entity
@Getter
@Setter
@Table(name = "tbl_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageList")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    private boolean delFlag;

    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();

    public void addImage(ProductImage image) {

        image.setOrd(imageList.size());
        imageList.add(image);

    }

    public void addImageString(String fileName) {

        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();

        addImage(productImage);
    }

    public void clearList() {

        this.imageList.clear();
    }



}
