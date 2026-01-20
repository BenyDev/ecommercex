package com.s23358.ecommercex.category.controller;

import com.s23358.ecommercex.category.dto.CategoryDto;
import com.s23358.ecommercex.category.entity.Category;
import com.s23358.ecommercex.category.service.CategoryService;

import com.s23358.ecommercex.res.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;



    @GetMapping
    public ResponseEntity<Response<Page<CategoryDto>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size

    ) {
        return ResponseEntity.ok(categoryService.getAllCategories(page, size));
    }

    @PostMapping
    public ResponseEntity<Response<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto category){
        return ResponseEntity.ok(categoryService.creteCategory(category.getName()));
    }
}
