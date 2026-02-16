package com.s23358.ecommercex.product.controller;

import com.s23358.ecommercex.product.service.impl.FileStorageService;
import com.s23358.ecommercex.res.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductImageController {

    private final FileStorageService fileStorageService;

    @PostMapping("/images")
    @ResponseStatus(HttpStatus.OK)
    public Response<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        List<String> urls = fileStorageService.saveProductImages(files);

        return Response.<List<String>>builder()
                .statusCode(200)
                .message("Images uploaded")
                .data(urls)
                .build();
    }

}

