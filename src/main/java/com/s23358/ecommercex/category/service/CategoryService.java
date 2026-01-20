package com.s23358.ecommercex.category.service;

import com.s23358.ecommercex.category.dto.CategoryDto;
import com.s23358.ecommercex.res.Response;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Response<Page<CategoryDto>> getAllCategories(int page, int size);

    Response<CategoryDto> creteCategory(String name);

}
