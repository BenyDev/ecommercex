package com.s23358.ecommercex.category.controller;

import com.s23358.ecommercex.category.dto.CategoryCreateRequest;
import com.s23358.ecommercex.category.dto.CategoryResponse;
import com.s23358.ecommercex.category.dto.CategoryUpdateRequest;
import com.s23358.ecommercex.category.service.CategoryService;

import com.s23358.ecommercex.res.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;



    @GetMapping
    public ResponseEntity<Response<Page<CategoryResponse>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) {
        return ResponseEntity.ok(categoryService.getAllCategories(page, size));
    }

    @PostMapping
    public ResponseEntity<Response<CategoryResponse>> createCategory(@Valid @RequestBody CategoryCreateRequest request){
        return ResponseEntity.ok(categoryService.creteCategory(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<CategoryResponse>> updateCategory(
            @PathVariable  Long id,
            @Valid @RequestBody CategoryUpdateRequest request){
        return ResponseEntity.ok(categoryService.updateCategory(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteCategory(@PathVariable  Long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryResponse>> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
