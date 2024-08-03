package org.zerok.mall.controller;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerok.mall.dto.ProductDto;
import org.zerok.mall.util.CustomFileUtil;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;

    @PostMapping("/")
    public Map<String, String> register(ProductDto productDto) {

        log.info("register: " + productDto);

        List<MultipartFile> files = productDto.getFiles();

        List<String> uploadedFileNames = fileUtil.saveFiles(files);

        productDto.setUploadedFileNames(uploadedFileNames);

        log.info(uploadedFileNames);

        return Map.of("RESULT","SUCCESS");
    }

}
