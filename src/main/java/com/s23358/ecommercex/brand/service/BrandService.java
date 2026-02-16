package com.s23358.ecommercex.brand.service;

import com.s23358.ecommercex.brand.dto.BrandResponse;
import com.s23358.ecommercex.brand.dto.CreateBrandRequest;
import com.s23358.ecommercex.brand.dto.UpdateBrandRequest;
import com.s23358.ecommercex.res.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BrandService {

    Response<List<BrandResponse>> getAllBrands();
    Response<BrandResponse> createBrand(CreateBrandRequest request, MultipartFile logoFile);
    Response<BrandResponse> updateBrand(UpdateBrandRequest request, MultipartFile logoFile);
}
