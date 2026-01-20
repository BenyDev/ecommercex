package com.s23358.ecommercex.product.service.impl;

import com.s23358.ecommercex.brand.entity.Brand;
import com.s23358.ecommercex.brand.repository.BrandRepository;
import com.s23358.ecommercex.category.entity.Category;
import com.s23358.ecommercex.category.repository.CategoryRepository;
import com.s23358.ecommercex.exception.NotFoundException;
import com.s23358.ecommercex.product.dto.CreateProductRequest;
import com.s23358.ecommercex.product.dto.EditProductRequest;
import com.s23358.ecommercex.product.entity.Product;
import com.s23358.ecommercex.product.repository.ProductRepository;
import com.s23358.ecommercex.product.service.ProductService;
import com.s23358.ecommercex.res.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Response<CreateProductRequest> createProduct(CreateProductRequest request) {

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new NotFoundException("Brand not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .unit(request.getUnit())
                .stockQuantity(request.getStockQuantity())
                .weight(request.getWeight())
                .images(request.getImages() == null ? new ArrayList<>() : new ArrayList<>(request.getImages()))
                .isActive(request.isActive())
                .brand(brand)
                .belongsToCategory(category)
                .build();

        productRepository.save(product);

        return Response.<CreateProductRequest>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Product created successfully")
                .data(request)
                .build();
    }

    @Override
    public Response<EditProductRequest> editProduct(EditProductRequest editProductRequest) {

        Product productToEdit = productRepository.findById(editProductRequest.getId()).
                orElseThrow(() -> new NotFoundException("Product not found"));

        Brand brand = brandRepository.findById(editProductRequest.getBrandId())
                        .orElseThrow(() -> new NotFoundException("Brand not found"));

        Category category = categoryRepository.findById(editProductRequest.getBrandId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        productToEdit.setName(editProductRequest.getName());
        productToEdit.setPrice(editProductRequest.getPrice());
        productToEdit.setDescription(editProductRequest.getDescription());
        productToEdit.setUnit(editProductRequest.getUnit());
        productToEdit.setStockQuantity(editProductRequest.getStockQuantity());
        productToEdit.setWeight(editProductRequest.getWeight());
        productToEdit.setImages(editProductRequest.getImages());
        productToEdit.setActive(editProductRequest.getIsActive());
        productToEdit.setBrand(brand);
        productToEdit.setBelongsToCategory(category);

        productRepository.save(productToEdit);

        EditProductRequest patchProduct = modelMapper.map(productToEdit, EditProductRequest.class);


        return Response.<EditProductRequest>builder()
                .statusCode(HttpStatus.OK.value())
                .data(patchProduct)
                .message("Product updated")
                .build();
    }
}
