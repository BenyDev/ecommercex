package com.s23358.ecommercex.product.service;

import com.s23358.ecommercex.product.dto.CreateProductRequest;
import com.s23358.ecommercex.product.dto.EditProductRequest;
import com.s23358.ecommercex.product.dto.ProductResponse;
import com.s23358.ecommercex.res.Response;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Response<ProductResponse>  createProduct(@Valid @RequestBody CreateProductRequest createProductRequest, List<MultipartFile> files);
    Response<ProductResponse>  editProduct(@Valid @RequestBody EditProductRequest editProductRequest,List<MultipartFile> files);
    Response<Page<ProductResponse>>  getAllByCategoryId(Long categoryId, int page, int size);
    Response<ProductResponse> getProductById(Long id);
    Response<Void> deleteProduct(Long id);
}
