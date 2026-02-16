package com.s23358.ecommercex.brand.controller;

import com.s23358.ecommercex.brand.dto.BrandResponse;
import com.s23358.ecommercex.brand.dto.CreateBrandRequest;
import com.s23358.ecommercex.brand.dto.UpdateBrandRequest;
import com.s23358.ecommercex.brand.service.BrandService;
import com.s23358.ecommercex.res.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/brand")
@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<Response<List<BrandResponse>>>getAllBrands(){
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<BrandResponse>> createBrand(
            @RequestPart("data") @Valid CreateBrandRequest request,
            @RequestPart("logo") MultipartFile logo
    ) {
        return ResponseEntity.ok(brandService.createBrand(request, logo));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<BrandResponse>> editBrand(
            @RequestPart("data") @Valid UpdateBrandRequest request,
            @RequestPart(value = "logo", required = false) MultipartFile logo
    ) {
        return ResponseEntity.ok(brandService.updateBrand(request, logo));
    }
}
