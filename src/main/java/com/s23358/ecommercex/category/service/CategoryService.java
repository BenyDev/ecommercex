package com.s23358.ecommercex.category.service;

import com.s23358.ecommercex.category.dto.CategoryCreateRequest;
import com.s23358.ecommercex.category.dto.CategoryResponse;
import com.s23358.ecommercex.category.dto.CategoryUpdateRequest;
import com.s23358.ecommercex.res.Response;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Response<Page<CategoryResponse>> getAllCategories(int page, int size);

    Response<CategoryResponse> creteCategory(CategoryCreateRequest request);
    Response<CategoryResponse> updateCategory(Long id, CategoryUpdateRequest request);

    Response<Void> deleteCategory(Long id);

    Response<CategoryResponse> getCategoryById(Long id);

}
