package com.s23358.ecommercex.product.service;

import com.s23358.ecommercex.product.dto.CreateProductRequest;
import com.s23358.ecommercex.product.dto.EditProductRequest;
import com.s23358.ecommercex.res.Response;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductService {
    Response<CreateProductRequest>  createProduct(@Valid @RequestBody CreateProductRequest createProductRequest);
    Response<EditProductRequest>  editProduct(@Valid @RequestBody EditProductRequest editProductRequest);
}
