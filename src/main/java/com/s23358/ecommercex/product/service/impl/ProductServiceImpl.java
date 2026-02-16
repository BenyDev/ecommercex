package com.s23358.ecommercex.product.service.impl;

import com.s23358.ecommercex.brand.entity.Brand;
import com.s23358.ecommercex.brand.repository.BrandRepository;
import com.s23358.ecommercex.category.entity.Category;
import com.s23358.ecommercex.category.repository.CategoryRepository;
import com.s23358.ecommercex.exception.NotFoundException;
import com.s23358.ecommercex.product.dto.CreateProductRequest;
import com.s23358.ecommercex.product.dto.EditProductRequest;
import com.s23358.ecommercex.product.dto.ProductResponse;
import com.s23358.ecommercex.product.entity.Product;
import com.s23358.ecommercex.product.repository.ProductRepository;
import com.s23358.ecommercex.product.service.ProductService;
import com.s23358.ecommercex.res.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Response<ProductResponse> createProduct(CreateProductRequest request) {

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

        Product saved = productRepository.save(product);

        ProductResponse productResponse = productToProductResponse(saved);

        return Response.<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Product created successfully")
                .data(productResponse)
                .build();
    }

    @Override
    public Response<ProductResponse> editProduct(EditProductRequest editProductRequest) {

        Product productToEdit = productRepository.findById(editProductRequest.getId()).
                orElseThrow(() -> new NotFoundException("Product not found"));

        Brand brand = brandRepository.findById(editProductRequest.getBrandId())
                        .orElseThrow(() -> new NotFoundException("Brand not found"));

        Category category = categoryRepository.findById(editProductRequest.getBelongsToCategory())
                .orElseThrow(() -> new NotFoundException("Category not found"));



        productToEdit.setName(editProductRequest.getName());
        productToEdit.setPrice(editProductRequest.getPrice());
        productToEdit.setDescription(editProductRequest.getDescription());
        productToEdit.setUnit(editProductRequest.getUnit());
        productToEdit.setStockQuantity(editProductRequest.getStockQuantity());
        productToEdit.setWeight(editProductRequest.getWeight());
        productToEdit.setActive(editProductRequest.getIsActive());
        productToEdit.setBrand(brand);
        productToEdit.setBelongsToCategory(category);

        if(editProductRequest.getImages() != null) {
            productToEdit.setImages(new ArrayList<>(editProductRequest.getImages()));
        }

        productRepository.save(productToEdit);

        Product saved = productRepository.save(productToEdit);
        ProductResponse response = productToProductResponse(saved);

        return Response.<ProductResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .message("Product updated")
                .build();
    }

    @Override
    public Response<Page<ProductResponse>> getAllByCategoryId(Long categoryId, int page, int size) {
        PageRequest pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<ProductResponse> responses = productRepository
                .findAllByBelongsToCategory_Id(categoryId, pageable)
                .map(this::productToProductResponse);

        return Response.<Page<ProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Products fetched")
                .data(responses)
                .build();
    }

    private ProductResponse productToProductResponse(Product p){
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .unit(p.getUnit())
                .description(p.getDescription())
                .stockQuantity(p.getStockQuantity())
                .weight(p.getWeight())
                .isActive(p.isActive())
                .images(p.getImages() == null ? new ArrayList<>() : new ArrayList<>(p.getImages()))
                .brandId(p.getBrand() != null ? p.getBrand().getId() : null)
                .categoryId(p.getBelongsToCategory() != null ? p.getBelongsToCategory().getId() : null)
                .build();
    }


}
