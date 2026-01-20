package com.s23358.ecommercex.brand.service.impl;

import com.s23358.ecommercex.brand.entity.Brand;
import com.s23358.ecommercex.brand.entity.BrandDto;
import com.s23358.ecommercex.brand.repository.BrandRepository;
import com.s23358.ecommercex.brand.service.BrandService;
import com.s23358.ecommercex.exception.BadRequestException;
import com.s23358.ecommercex.res.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response<List<Brand>> getAllBrands() {

        List<Brand> brands = brandRepository.findAll();

        return Response.<List<Brand>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Brands").data(brands).build();
    }

    @Override
    public Response<Brand> createBrand(BrandDto brand) {

        if(brandRepository.findByName(brand.getName()).isPresent())
            throw new BadRequestException(String.format("Brand with name %s already exists", brand.getName()));

        Brand brandEntity = modelMapper.map(brand, Brand.class);

        brandRepository.save(brandEntity);

        return Response.<Brand>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Brand created")
                .data(brandEntity)
                .build();
    }
}
