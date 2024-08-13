package org.zerok.mall.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    private boolean delFlag;

    // 업로드가 필요한 파일
    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

    // 이미 업로드 된 파일
    @Builder.Default
    private List<String> uploadedFileNames = new ArrayList<>();

}
