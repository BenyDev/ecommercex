package com.s23358.ecommercex.category.service.impl;

import com.s23358.ecommercex.category.dto.CategoryCreateRequest;
import com.s23358.ecommercex.category.dto.CategoryResponse;
import com.s23358.ecommercex.category.dto.CategoryUpdateRequest;
import com.s23358.ecommercex.category.entity.Category;
import com.s23358.ecommercex.category.repository.CategoryRepository;
import com.s23358.ecommercex.category.service.CategoryService;
import com.s23358.ecommercex.exception.BadRequestException;
import com.s23358.ecommercex.exception.NotFoundException;
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
    public Response<Page<CategoryResponse>> getAllCategories(int page, int size) {
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(page, size));

        Page<CategoryResponse> categoryDtos = categories.map(category -> modelMapper.map(category, CategoryResponse.class) );

        return Response.<Page<CategoryResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Categories retrieved")
                .data(categoryDtos)
                .build();
    }

    @Override
    public Response<CategoryResponse> creteCategory(CategoryCreateRequest request) {

        String name = request.getName().trim().toLowerCase();

        if (categoryRepository.findByName(name).isPresent() )
            throw new BadRequestException(String.format("Category with name %s already exists", name));

        Category category = Category.builder()
                .name(name)
                .build();


        categoryRepository.save(category);

        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);

        return Response.<CategoryResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Category added successful")
                .data(categoryResponse)
                .build();
    }

    @Override
    public Response<CategoryResponse> updateCategory(Long id, CategoryUpdateRequest request) {

        String name = request.getName()
                .trim()
                .replaceAll("\\s{2,}"," ")
                .toLowerCase();

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Category with id %d not found", id))

        );

        if(categoryRepository.findByName(name).isPresent()){

            throw new BadRequestException(String.format("Category with name %s already exists", name));

        }

        category.setName(name);

        categoryRepository.save(category);

        CategoryResponse response = modelMapper.map(category, CategoryResponse.class);

        return Response.<CategoryResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Category updated successful")
                .data(response)
                .build();

    }

    @Override
    public Response<Void> deleteCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Category with id %d not found", id))
        );

        String categoryName = category.getName();

        categoryRepository.delete(category);
        return Response.<Void>builder()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .message(String.format("Category %s deleted successful",categoryName))
                .build();
    }

    @Override
    public Response<CategoryResponse> getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id +" not found"));

        CategoryResponse response = modelMapper.map(category, CategoryResponse.class);

        return Response.<CategoryResponse>builder()
                .message("Fetch success")
                .data(response)
                .statusCode(200)
                .build();

    }
}
