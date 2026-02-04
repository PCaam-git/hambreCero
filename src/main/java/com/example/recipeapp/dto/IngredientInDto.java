package com.example.recipeapp.dto;


import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientInDto {
    @NotEmpty(message = "Ingredient name is required")
    private String name;

    private int calories;

    @NotNull(message = "Season is required")
    @Pattern(regexp = "SPRING|SUMMER|AUTUMN|WINTER", message = "Season must be SPRING, SUMMER, AUTUMN or WINTER")
    private String season;

    @NotNull(message = "isOrganic field is required")
    private Boolean isOrganic;

    private LocalDate harvestDate;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price per kg must be positive")
    private BigDecimal priceKg;

    @NotNull(message = "Carbon footprint is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Carbon footprint must be positive")
    private BigDecimal carbonFootprint;
}
