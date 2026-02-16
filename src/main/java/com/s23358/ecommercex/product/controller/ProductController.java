package com.s23358.ecommercex.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s23358.ecommercex.enums.Unit;
import com.s23358.ecommercex.product.dto.CreateProductRequest;
import com.s23358.ecommercex.product.dto.EditProductRequest;
import com.s23358.ecommercex.product.dto.ProductResponse;
import com.s23358.ecommercex.product.entity.Product;
import com.s23358.ecommercex.product.repository.ProductRepository;
import com.s23358.ecommercex.product.service.ProductService;
import com.s23358.ecommercex.res.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<ProductResponse>>  createProduct(
            @RequestPart("data") @Valid  CreateProductRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files

    ){
        return ResponseEntity.ok(productService.createProduct(request,files));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Response<Page<ProductResponse>>> getAllProductsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(productService.getAllByCategoryId(categoryId,page,size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductResponse>> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/units")
    public List<String> getAllUnits(){
        return Arrays.stream(Unit.values())
                .map(Enum::name).toList();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<ProductResponse>> editProduct(
             @RequestPart("data") @Valid EditProductRequest request,
             @RequestPart(value = "files",  required = false) List<MultipartFile> files
    ){
        return ResponseEntity.ok(productService.editProduct(request,files));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
