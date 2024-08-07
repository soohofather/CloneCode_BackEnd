package org.zerok.mall.controller;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerok.mall.dto.PageRequestDto;
import org.zerok.mall.dto.PageResponseDto;
import org.zerok.mall.dto.ProductDto;
import org.zerok.mall.service.ProductService;
import org.zerok.mall.util.CustomFileUtil;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;

//    @PostMapping("/")
//    public Map<String, String> register(ProductDto productDto) {
//
//        log.info("register: " + productDto);
//
//        List<MultipartFile> files = productDto.getFiles();
//
//        List<String> uploadedFileNames = fileUtil.saveFiles(files);
//
//        productDto.setUploadedFileNames(uploadedFileNames);
//
//        log.info(uploadedFileNames);
//
//        return Map.of("RESULT","SUCCESS");
//    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(
            @PathVariable("fileName") String fileName
    ) {
        return fileUtil.getFile((fileName));
    }

    @GetMapping("/list")
    public PageResponseDto<ProductDto> list(PageRequestDto pageRequestDto) {

        return productService.getList(pageRequestDto);
    }

    @PostMapping("/")
    public Map<String, Long> register(ProductDto productDto) {

        List<MultipartFile> files = productDto.getFiles();

        List<String> uploadFileNames = fileUtil.saveFiles(files);

        productDto.setUploadedFileNames(uploadFileNames);

        log.info(uploadFileNames);

        Long pno = productService.register(productDto);

        return Map.of("result", pno);
    }

    @GetMapping("/{pno}")
    public ProductDto read(
            @PathVariable("pno") Long pno
    ) {
        return productService.get(pno);
    }
}
