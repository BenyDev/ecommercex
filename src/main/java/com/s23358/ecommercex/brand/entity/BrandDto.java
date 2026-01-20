package com.s23358.ecommercex.brand.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String shortName;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String logo;
}
