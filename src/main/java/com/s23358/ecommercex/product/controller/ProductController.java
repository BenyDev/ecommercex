package com.s23358.ecommercex.product.controller;

import com.s23358.ecommercex.enums.Unit;
import com.s23358.ecommercex.product.dto.CreateProductRequest;
import com.s23358.ecommercex.product.dto.EditProductRequest;
import com.s23358.ecommercex.product.entity.Product;
import com.s23358.ecommercex.product.repository.ProductRepository;
import com.s23358.ecommercex.product.service.ProductService;
import com.s23358.ecommercex.res.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Response<CreateProductRequest>>  createProduct(@Valid @RequestBody CreateProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProductsByCategoryId(@RequestParam Long categoryId){
        return ResponseEntity.ok(productRepository.findAllByBelongsToCategory_Id(categoryId));
    }

    @GetMapping("/units")
    public List<String> getAllUnits(){
        return Arrays.stream(Unit.values())
                .map(Enum::name).toList();
    }

    @PutMapping
    public ResponseEntity<Response<EditProductRequest>> editProduct(@Valid @RequestBody EditProductRequest request){
        return ResponseEntity.ok(productService.editProduct(request));
    }
}
