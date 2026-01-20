package com.s23358.ecommercex.brand.controller;

import com.s23358.ecommercex.brand.entity.Brand;
import com.s23358.ecommercex.brand.entity.BrandDto;
import com.s23358.ecommercex.brand.service.BrandService;
import com.s23358.ecommercex.res.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/brand")
@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<Response<List<Brand>>>getAllBrands(){
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @PostMapping
    public ResponseEntity<Response<Brand>> createBrand(@RequestBody BrandDto brandDto){
        return ResponseEntity.ok(brandService.createBrand(brandDto));
    }
}
