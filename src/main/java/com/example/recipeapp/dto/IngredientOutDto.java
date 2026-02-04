package com.example.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientOutDto {
    private Long id;
    private String name;
    private int calories;
    private String season;
    private boolean isOrganic;
    private LocalDate harvestDate;
    private BigDecimal priceKg;
    private BigDecimal carbonFootprint;
}
