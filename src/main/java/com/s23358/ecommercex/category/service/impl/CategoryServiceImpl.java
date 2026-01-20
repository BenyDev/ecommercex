package com.s23358.ecommercex.category.service.impl;

import com.s23358.ecommercex.category.dto.CategoryDto;
import com.s23358.ecommercex.category.entity.Category;
import com.s23358.ecommercex.category.repository.CategoryRepository;
import com.s23358.ecommercex.category.service.CategoryService;
import com.s23358.ecommercex.exception.BadRequestException;
import com.s23358.ecommercex.res.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response<Page<CategoryDto>> getAllCategories(int page, int size) {
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(page, size));

        Page<CategoryDto> categoryDtos = categories.map(category -> modelMapper.map(category, CategoryDto.class) );

        return Response.<Page<CategoryDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Categories retrieved")
                .data(categoryDtos)
                .build();
    }

    @Override
    public Response<CategoryDto> creteCategory(String name) {

        name = name.toLowerCase();

        if (categoryRepository.findByName(name).isPresent() )
            throw new BadRequestException(String.format("Category with name %s already exists", name));

        Category category = Category.builder()
                .name(name)
                .build();

        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);

        categoryRepository.save(category);

        return Response.<CategoryDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Category added successful")
                .data(categoryDto)
                .build();
    }
}
