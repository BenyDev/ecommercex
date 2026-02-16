package com.s23358.ecommercex.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.s23358.ecommercex.enums.Unit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditProductRequest {

    @NotNull
    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    private String description;

    @NotNull
    private Unit unit;

    @NotNull
    @Min(0)
    private Integer stockQuantity;

    @NotNull
    @Positive
    private BigDecimal weight;

    @Builder.Default
    private List<@NotBlank String> images = new ArrayList<>();

    @NotNull
    private Boolean isActive;

    @NotNull
    private Long brandId;

    @NotNull
    private Long belongsToCategory;
}
