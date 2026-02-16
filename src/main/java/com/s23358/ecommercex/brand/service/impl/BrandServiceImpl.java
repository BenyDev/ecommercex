package com.s23358.ecommercex.brand.service.impl;

import com.s23358.ecommercex.brand.dto.BrandResponse;
import com.s23358.ecommercex.brand.dto.CreateBrandRequest;
import com.s23358.ecommercex.brand.dto.UpdateBrandRequest;
import com.s23358.ecommercex.brand.entity.*;
import com.s23358.ecommercex.brand.repository.BrandRepository;
import com.s23358.ecommercex.brand.service.BrandService;
import com.s23358.ecommercex.exception.BrandImageStorageException;
import com.s23358.ecommercex.exception.NotFoundException;
import com.s23358.ecommercex.product.service.impl.FileStorageService;
import com.s23358.ecommercex.res.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final FileStorageService fileStorageService;



    private BrandResponse brandToBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .shortName(brand.getShortName())
                .description(brand.getDescription())
                .logo(brand.getLogo())
                .build();
    }

    @Override
    public Response<List<BrandResponse>> getAllBrands() {

        List<Brand> brands = brandRepository.findAll();

        List<BrandResponse> brandResponseList = brands.stream().map(this::brandToBrandResponse).toList();

        return Response.<List<BrandResponse>>builder()
                .data(brandResponseList)
                .message("Brands fetched successfully")
                .statusCode(200)
                .build();
    }

    @Override
    @Transactional
    public Response<BrandResponse> createBrand(CreateBrandRequest request, MultipartFile logoFile) {
        String logoUrl = null;

        try {
            logoUrl = fileStorageService.saveBrandLogo(logoFile);

            Brand brand = Brand.builder()
                    .name(request.getName())
                    .shortName(request.getShortName())
                    .description(request.getDescription())
                    .logo(logoUrl)
                    .build();

            Brand saved = brandRepository.save(brand);

            return Response.<BrandResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Brand created successfully")
                    .data(brandToBrandResponse(saved))
                    .build();

        } catch (Exception e) {
            if (logoUrl != null) {
                fileStorageService.deleteByUrls(List.of(logoUrl));
            }
            throw e;
        }
    }

    @Override
    @Transactional
    public Response<BrandResponse> updateBrand(UpdateBrandRequest request, MultipartFile logoFile) {

        String newLogoUrl = null;

        try {
            Brand brand = brandRepository.findById(request.getId())
                    .orElseThrow(() -> new NotFoundException("Brand not found"));

            String oldLogoUrl = brand.getLogo();


            if (logoFile != null && !logoFile.isEmpty()) {
                newLogoUrl = fileStorageService.saveBrandLogo(logoFile);
                brand.setLogo(newLogoUrl);
            }

            brand.setName(request.getName());
            brand.setShortName(request.getShortName());
            brand.setDescription(request.getDescription());

            Brand saved = brandRepository.save(brand);


            if (newLogoUrl != null && oldLogoUrl != null) {
                fileStorageService.deleteByUrls(List.of(oldLogoUrl));
            }

            return Response.<BrandResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Brand updated")
                    .data(brandToBrandResponse(saved))
                    .build();

        } catch (Exception e) {
            if (newLogoUrl != null) {
                fileStorageService.deleteByUrls(List.of(newLogoUrl));
            }
            throw new BrandImageStorageException("Failed to update brand logo", e);
        }
    }
}
