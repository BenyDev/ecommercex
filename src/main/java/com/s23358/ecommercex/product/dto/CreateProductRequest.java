package com.s23358.ecommercex.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.s23358.ecommercex.enums.Unit;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {


    @NotBlank
    private String name;

    @DecimalMin(value = "0.00", inclusive = false)
    @NotNull
    private BigDecimal price;

    @NotNull
    private Unit unit;

    @NotNull
    @NotBlank
    private String description;

    @Min(0)
    private int stockQuantity;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal weight;

    @JsonProperty("active")
    private boolean isActive;

    @NotNull
    private Long brandId;

    @NotNull
    private Long categoryId;

}
