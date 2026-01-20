package com.s23358.ecommercex.brand.service;

import com.s23358.ecommercex.brand.entity.Brand;
import com.s23358.ecommercex.brand.entity.BrandDto;
import com.s23358.ecommercex.res.Response;

import java.util.List;

public interface BrandService {

    Response<List<Brand>> getAllBrands();
    Response<Brand> createBrand(BrandDto brand);
}
