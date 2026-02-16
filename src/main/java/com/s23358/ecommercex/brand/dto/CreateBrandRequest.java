package com.s23358.ecommercex.brand.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateBrandRequest {


    @NotBlank
    private String name;


    @NotBlank
    private String shortName;


    @NotBlank
    private String description;


}
